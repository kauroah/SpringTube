package org.example.springtube.controllers;

import org.example.springtube.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    // Injecting the MailService bean
    @Autowired
    private MailService mailService;

    /**
     * Displays the contact page.
     *
     * @return the name of the view to render the contact page
     */
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }

    /**
     * Handles the submission of the contact form and sends an email.
     *
     * @param name    the name of the person contacting
     * @param email   the email address of the person contacting
     * @param message the message from the contact form
     * @return a redirect to the thank you page
     */
    @PostMapping("/contact")
    public String sendEmail(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        // Constructing the email subject and content
        String subject = "New contact from " + name;
        String content = "Message from: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message;
        // Sending the email using the mail service
        mailService.sendSimpleEmail("ahmedabdelhady0246@gmail.com", subject, content);
        // Redirecting to the thank you page
        return "redirect:/thank-you";
    }

    /**
     * Displays the thank you page after a contact form submission.
     *
     * @return the name of the view to render the thank you page
     */
    @GetMapping("/thank-you")
    public String showThanksPage() {
        return "thank-you";
    }
}