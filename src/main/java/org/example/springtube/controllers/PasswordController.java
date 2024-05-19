package org.example.springtube.controllers;

import org.example.springtube.models.User;
import org.example.springtube.services.MailService;
import org.example.springtube.services.PasswordService;
import org.example.springtube.services.SignUpService;
import org.example.springtube.tokens.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PasswordController {

    // Injecting the necessary services
    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private MailService mailService;

    /**
     * Displays the forgot password form.
     *
     * @return the name of the view to render the forgot password form
     */
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    /**
     * Handles the submission of the forgot password form.
     *
     * @param email   the email address of the user requesting a password reset
     * @param request the HttpServletRequest to get the current request URL
     * @return a redirect to the forgot password success page
     */
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        // Find the user by email
        User user = signUpService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a password reset token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        passwordService.save(resetToken);

        // Construct the password reset URL
        String resetUrl = request.getRequestURL().toString().replace("/forgotPassword", "/resetPassword?token=" + token);
        // Send the password reset email
        mailService.sendPasswordResetEmail(email, resetUrl);

        return "redirect:/forgotPasswordSuccess";
    }

    /**
     * Displays the forgot password success page.
     *
     * @return the name of the view to render the forgot password success page
     */
    @GetMapping("/forgotPasswordSuccess")
    public String showForgotPasswordSuccessPage() {
        return "forgotPasswordSuccess";
    }

    /**
     * Displays the reset password form.
     *
     * @param token the password reset token
     * @param model the model to add attributes to
     * @return the name of the view to render the reset password form
     */
    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Find the reset token
        PasswordResetToken resetToken = passwordService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Check if the token has expired
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/forgotPassword";
        }

        model.addAttribute("token", token);
        return "resetpassword";
    }

    /**
     * Handles the submission of the reset password form.
     *
     * @param token    the password reset token
     * @param password the new password
     * @return a redirect to the reset password success page
     */
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        // Find the reset token
        PasswordResetToken resetToken = passwordService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Check if the token has expired
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/forgotPassword";
        }

        // Update the user's password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        signUpService.save(user);

        // Delete the used reset token
        passwordService.delete(resetToken);

        return "redirect:/resetPasswordSuccess";
    }

    /**
     * Displays the reset password success page.
     *
     * @return the name of the view to render the reset password success page
     */
    @GetMapping("/resetPasswordSuccess")
    public String showResetPasswordSuccessPage() {
        return "resetPasswordSuccess";
    }
}