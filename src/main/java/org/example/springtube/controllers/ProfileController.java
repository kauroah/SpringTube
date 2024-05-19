package org.example.springtube.controllers;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class ProfileController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserService userService;

    /**
     * Displays the profile page of the currently logged-in user.
     *
     * @param model   the model to add attributes to
     * @param request the HttpServletRequest to get the current user's email
     * @return the name of the view to render the profile page
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpServletRequest request) {
        try {
            String email = request.getUserPrincipal().getName(); // Get the email of the currently logged-in user
            User user = signUpService.getUserByUsername(email); // Fetch the user by email
            if (user == null) {
                throw new RuntimeException("User not found"); // Throw an exception if user is not found
            }
            model.addAttribute("user", user); // Add the user object to the model
            log.info("Profile page accessed successfully for user {}", email);
            return "profile_page"; // Return the profile page view
        } catch (Exception e) {
            // If an unexpected error occurs, set the error message and redirect to the error page
            log.info("An unexpected error occurred while processing profile page request", e);
            model.addAttribute("errorMessage", "An unexpected error occurred while processing your request. Please try again later.");
            return "redirect:/error";
        }
    }

    /**
     * Handles the submission of the profile update form.
     *
     * @param firstName the updated first name
     * @param lastName  the updated last name
     * @param phone     the updated phone number
     * @param password  the updated password
     * @return a redirect to the profile page
     */
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("phone") String phone,
                                @RequestParam("password") String password,
                                Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName(); // Get the username (email) of the currently logged-in user
            signUpService.updateUserProfile(email, firstName, lastName, phone, password); // Update the user profile
            log.info("Profile updated successfully for user {}", email);
            return "redirect:/profile"; // Redirect to the profile page
        } catch (Exception e) {
            // If an unexpected error occurs, set the error message and redirect to the error page
            log.info("An unexpected error occurred while updating user profile", e);
            model.addAttribute("errorMessage", "An unexpected error occurred while updating your profile. Please try again later.");
            return "redirect:/error";
        }
    }
}
