package org.example.springtube.controllers;

import org.example.springtube.models.User;
import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class SignInController {

    @Autowired
    private SignUpService signUpService;

    // Endpoint to display the sign-in page
    @GetMapping("/signIn")
    public String getSignInPage(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        return "sign_in_page";
    }

    // Endpoint to handle sign-in form submission
    @PostMapping("/signInPage")
    public String signIn(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        User user = signUpService.authenticateAndGetUserId(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            System.out.println("USER ID: " + user.getId());
            return "redirect:/springtube";
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/signIn";
        }
    }
}
