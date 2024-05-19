package org.example.springtube.controllers;

import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConfirmController {

    // Injecting the SignUpService bean
    @Autowired
    private SignUpService signUpService;

    /**
     * Confirms a user's account using a confirmation token.
     *
     * @param code                the confirmation token
     * @param redirectAttributes  attributes for flash attributes (temporary storage for one request)
     * @return the name of the view to render based on the result of the confirmation
     */
    @GetMapping("/confirm/{token}")
    public String confirmUser(@PathVariable("token") String code, RedirectAttributes redirectAttributes) {
        // Attempt to confirm the user with the provided token
        boolean confirmed = signUpService.confirmUser(code);
        if (confirmed) {
            // If confirmation is successful, add a success message and redirect to the success page
            redirectAttributes.addFlashAttribute("success", "Your account has been successfully confirmed!");
            return "confirmation_success";
        } else {
            // If confirmation fails, add an error message and redirect to the error page
            redirectAttributes.addFlashAttribute("error", "Confirmation failed. Invalid or expired confirmation code.");
            return "confirmation_error";
        }
    }
}