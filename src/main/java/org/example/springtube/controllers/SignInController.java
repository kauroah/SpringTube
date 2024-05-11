package org.example.springtube.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

    // Endpoint to display the sign-in page
    @GetMapping("/signIn")
    public ModelAndView getSignInPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("sign_in_page");
        if (error != null) {
            modelAndView.addObject("message", "Invalid username or password!");
        }
        return modelAndView;
    }

    // This method is not necessarily needed unless you're handling logins directly
    @PostMapping("/signIn")
    public String signIn(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam(value = "remember-me", required = false) boolean rememberMe) {
        // Normally, Spring Security handles this automatically
        // This method is just for understanding, you should let Spring Security handle POST to /signIn

        // You would process the remember-me checkbox value here if you were manually handling authentication
        // This is more for conceptual purposes; Spring Security should be configured to handle this as shown previously

        return "redirect:/springtube"; // Redirect on success
    }
}

