package org.example.springtube.controllers;

import org.example.springtube.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private MailService mailService;


    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }

    @PostMapping("/contact")
    public String sendEmail(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        String subject = "New contact from " + name;
        String content = "Message from: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message;
        mailService.sendSimpleEmail("ahmedabdelhady0246@gmail.com", subject, content);
        return "redirect:/thank-you";
    }



    @GetMapping("/thank-you")
    public String showThanksPage(){
        return "thank-you";
    }
}
