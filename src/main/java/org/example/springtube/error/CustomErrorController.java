package org.example.springtube.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        // Get the HTTP error status code
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Check if statusCode is null
        if (statusCode == null) {
            // Handle the case when statusCode is null
            String errorMessage = "An unexpected error occurred. Please try again later.";
            model.addAttribute("errorMessage", errorMessage);
            return "custom_error";
        }

        // Check if it's an authentication failure (e.g., status code 401)
        if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password!");
            return "redirect:/signIn";
        }

        // Set the error message based on the status code
        String errorMessage;
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);
        if (httpStatus != null) {
            errorMessage = "Error " + statusCode + ": Something went wrong.";
        } else {
            errorMessage = "An error occurred. Please try again later.";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "custom_error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}