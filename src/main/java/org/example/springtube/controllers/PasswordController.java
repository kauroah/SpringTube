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
    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private MailService mailService;


    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }


    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        User user = signUpService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        passwordService.save(resetToken);

        String resetUrl = request.getRequestURL().toString().replace("/forgotPassword", "/resetPassword?token=" + token);
        mailService.sendPasswordResetEmail(email, resetUrl);

        return "redirect:/forgotPasswordSuccess";
    }

    @GetMapping("/forgotPasswordSuccess")
    public String showForgotPasswordSuccessPage() {
        return "forgotPasswordSuccess";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = passwordService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/forgotPassword";
        }

        model.addAttribute("token", token);
        return "resetpassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        PasswordResetToken resetToken = passwordService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "redirect:/forgotPassword";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        signUpService.save(user);

        passwordService.delete(resetToken);

        return "redirect:/resetPasswordSuccess";
    }

    @GetMapping("/resetPasswordSuccess")
    public String showResetPasswordSuccessPage() {
        return "resetPasswordSuccess";
    }

}

