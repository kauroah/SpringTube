package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.UserDto;
import org.example.springtube.dto.UserForm;
import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        log.info("Accessed sign up page");
        return "sign_up_page";
    }

    @PostMapping("/signUp")
    public String signUp(UserForm form, Model model) {
        try {
            signUpService.addUser(form);
            log.info("User signed up successfully: {}", form.getEmail());
            return "redirect:/verifyEmail";
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Email is already registered.")) {
                log.warn("Email {} is already registered", form.getEmail());
                return "redirect:/emailAlreadyRegistered";
            }
            log.info("Error occurred during sign up: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "sign_up_page";
        }
    }

    @GetMapping("/verifyEmail")
    public String getVerifyEmailPage() {
        log.info("Accessed verify email page");
        return "confirm_email";
    }

    @GetMapping("/emailAlreadyRegistered")
    public String getEmailAlreadyRegisteredPage() {
        log.info("Accessed email already registered page");
        return "alreadyRegistered";
    }
}
