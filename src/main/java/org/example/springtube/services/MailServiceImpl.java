package org.example.springtube.services;

import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MailServiceImpl implements MailService {
    // Injects the JavaMailSender interface to send emails
    @Autowired
    private JavaMailSender javaMailSender;

    // Injects the sender's email address from application properties.
    @Value("${spring.mail.username}")
    private String mailFrom;


    /**
     * Usage in the Code: When the sendEmailForConfirm method is called,
     * it utilizes the confirmMailTemplate to generate the email content.
     * It replaces placeholders in the template (like ${token})
     * with actual data (e.g., a confirmation code) to personalize the email.
     */
    private final Template confirmMailTemplate;

    /**
     * to generate the email content. Similar to the confirmation template,
     * it dynamically fills in placeholders in the template with actual data
     */
    private final Template resetPasswordTemplate;


    /**
     * Configuration Setup: A Configuration instance for FreeMarker is set up to manage templates.
     * It specifies UTF-8 encoding and uses a template loader appropriate for Spring applications.
     * Templates are loaded into confirmMailTemplate and resetPasswordTemplate during initialization.
     * If there is a failure in loading templates, it throws an IllegalStateException.
     */
    public MailServiceImpl() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new SpringTemplateLoader(new ClassRelativeResourceLoader(
                this.getClass()), "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            this.confirmMailTemplate = configuration.getTemplate("templates/confirm_mail.ftlh");
            this.resetPasswordTemplate = configuration.getTemplate("templates/reset_password.ftlh");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void sendEmailForConfirm(String email, String code) {
        String mailText = getEmailText(confirmMailTemplate, code);
        MimeMessagePreparator messagePreparator = prepareMessage(email, mailText, "Confirm your account");
        javaMailSender.send(messagePreparator);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String mailText = getEmailText(resetPasswordTemplate, token);
        MimeMessagePreparator messagePreparator = prepareMessage(email, mailText, "Reset your password");
        javaMailSender.send(messagePreparator);
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }


    /**
     *  is an interface in the Spring Framework, specifically part of the Spring Mail integration.
     * @param email
     * @param mailText
     * @param subject
     * @return
     */
    private MimeMessagePreparator prepareMessage(String email, String mailText, String subject) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailText, true);
        };
    }

    private String getEmailText(Template template, String token) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("token", token);
        StringWriter writer = new StringWriter();
        try {
            template.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }

        return writer.toString();
    }
}