package org.example.springtube.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignInController {

    @GetMapping("signIn")
    public String getSignInPage() {
        return "sign_in_page";
    }

    @PostMapping("/signIn")
    public String signInSuccess() {
        return "redirect:/springtube";
    }
}
