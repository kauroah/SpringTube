package org.example.springtube.controllers;

import org.example.springtube.dto.UserForm;
import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign_up_page";
    }

    @PostMapping("/signUp")
    public String SignUp(UserForm form, Model model) {
        try {
            signUpService.addUser(form);
            return "redirect:/signIn";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "sign_up_page"; // Return to signup page with error message
        }
    }
}
