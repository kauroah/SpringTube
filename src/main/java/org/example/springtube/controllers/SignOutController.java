package org.example.springtube.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignOutController {

    @GetMapping("/signOut")
    public String signOut() {
        SecurityContextHolder.clearContext();
        return "redirect:/springtube";
    }
}