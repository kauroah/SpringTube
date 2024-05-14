package org.example.springtube.controllers;

import org.example.springtube.models.User;
import org.example.springtube.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        // Check if the current user has the admin role
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // User has admin role, proceed to the admin dashboard
            List<User> users = userService.findAllUsers();
            model.addAttribute("users", users);
            return "adminDashboard";
        } else {
            return "redirect:/home";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "adminDashboard";
    }

    @PostMapping("/block")
    public String blockUser(@RequestParam Long userId) {
        userService.blockUser(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/unblock")
    public String unblockUser(@RequestParam Long userId) {
        userService.unblockUser(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/grantAdmin")
    public String grantAdminRole(@RequestParam Long userId) {
        userService.grantAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/revokeAdmin")
    public String revokeAdminRole(@RequestParam Long userId) {
        userService.revokeAdminRole(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard";
    }
}
