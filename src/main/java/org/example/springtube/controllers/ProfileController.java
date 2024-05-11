package org.example.springtube.controllers;

import org.example.springtube.models.User;
import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = signUpService.getUserByUsername(email);
        model.addAttribute("user", user);
        return "profile_page";
    }


    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("phone") String phone,
                                @RequestParam("password") String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Get the username of the currently logged-in user
        signUpService.updateUserProfile(email, firstName, lastName, phone, password);
        return "redirect:/profile";
    }
}
