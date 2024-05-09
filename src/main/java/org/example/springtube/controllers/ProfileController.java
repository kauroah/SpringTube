package org.example.springtube.controllers;
import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ProfileController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

    @GetMapping("/profile")
    public String getProfilePage(Model model, Principal principal) {
        String username = principal.getName();
        User user = signUpService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profile_page";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") User updatedUser, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            logger.log(Level.WARNING, "Validation errors occurred while updating profile.");
            return "profile_page";
        }

        String username = principal.getName();
        User user = signUpService.getUserByUsername(username);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());

        String newPassword = updatedUser.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
        }
        try {
          userRepository.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getPassword());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while updating user profile.", e);
            return "confirmation_error";
        }
        return "redirect:/springtube";
    }
}


