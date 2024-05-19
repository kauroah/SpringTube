package org.example.springtube.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SignOutController {

    @GetMapping("/signOut")
    public String signOut(HttpServletRequest request) {
        log.info("Attempting to sign out a user");

        /**
         * clears the Spring Security authentication from the SecurityContextHolder
         *  which effectively logs the user out from the security context of the application.
         */
        SecurityContextHolder.clearContext();
        log.info("Security context has been cleared");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("Session invalidated successfully");
        } else {
            log.info("No session to invalidate");
        }
        return "redirect:/signIn";
    }
}
