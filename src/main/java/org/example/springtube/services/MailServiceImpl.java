//package org.example.springtube.services;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateExceptionHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassRelativeResourceLoader;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.SpringTemplateLoader;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class MailServiceImpl implements MailService {
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String mailFrom;
//
//    private final Template confirmMailTemplate;
//
//
//    public MailServiceImpl() {
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
//        configuration.setDefaultEncoding("UTF-8");
//        configuration.setTemplateLoader(new SpringTemplateLoader(new ClassRelativeResourceLoader(
//                this.getClass()), "/"));
//        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        try {
//            this.confirmMailTemplate = configuration.getTemplate("templates/confirm_mail.ftlh");
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public void sendEmailForConfirm(String email, String code) {
//        String mailText = getEmailText(code);
//        MimeMessagePreparator messagePreparator = getEmail(email, mailText);
//        javaMailSender.send(messagePreparator);
//
//    }
//
//    private MimeMessagePreparator getEmail(String email, String mailText) {
//        MimeMessagePreparator messagePreparator =  mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom(mailFrom);
//            messageHelper.setTo(email);
//            messageHelper.setSubject("Confirm your account");
//            messageHelper.setText(mailText, true);
//        };
//        return messagePreparator;
//    }
//
//    private String getEmailText(String code) {
//        Map<String, String> attributes = new HashMap<>();
//        attributes.put("confirm_code", code);
//        StringWriter writer = new StringWriter();
//        try {
//            confirmMailTemplate.process(attributes, writer);
//        } catch (TemplateException | IOException e) {
//            throw new IllegalStateException(e);
//        }
//
//        return writer.toString();
//    }
//
//}



package org.example.springtube.services;

import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassRelativeResourceLoader;
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
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final Template confirmMailTemplate;
    private final Template resetPasswordTemplate; // Template for reset password

    public MailServiceImpl() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new SpringTemplateLoader(new ClassRelativeResourceLoader(
                this.getClass()), "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            this.confirmMailTemplate = configuration.getTemplate("templates/confirm_mail.ftlh");
            this.resetPasswordTemplate = configuration.getTemplate("templates/reset_password.ftlh"); // Load reset password template
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
