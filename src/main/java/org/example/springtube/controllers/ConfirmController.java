package org.example.springtube.controllers;

import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConfirmController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/confirm/{token}")
    public String confirmUser(@PathVariable("token") String code, RedirectAttributes redirectAttributes) {
        boolean confirmed = signUpService.confirmUser(code);
        if (!confirmed) {
            redirectAttributes.addFlashAttribute("success", "Your account has been successfully confirmed!");
            return "confirmation_error";
        } else {
            redirectAttributes.addFlashAttribute("error", "Confirmation failed. Invalid or expired confirmation code.");
            return "confirmation_success";
        }
    }
}