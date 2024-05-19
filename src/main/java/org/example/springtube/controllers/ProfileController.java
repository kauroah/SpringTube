package org.example.springtube.controllers;

import org.example.springtube.models.User;
import org.example.springtube.services.SignUpService;
import org.example.springtube.services.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * Displays the profile page of the currently logged-in user.
     *
     * @param model the model to add attributes to
     * @param request the HttpServletRequest to get the current user's email
     * @return the name of the view to render the profile page
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName(); // Get the email of the currently logged-in user
        User user = signUpService.getUserByUsername(email); // Fetch the user by email
        model.addAttribute("user", user); // Add the user object to the model
        return "profile_page"; // Return the profile page view
    }

    /**
     * Handles the submission of the profile update form.
     *
     * @param firstName the updated first name
     * @param lastName the updated last name
     * @param phone the updated phone number
     * @param password the updated password
     * @return a redirect to the profile page
     */
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("phone") String phone,
                                @RequestParam("password") String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Get the username (email) of the currently logged-in user
        signUpService.updateUserProfile(email, firstName, lastName, phone, password); // Update the user profile
        return "redirect:/profile"; // Redirect to the profile page
    }
}