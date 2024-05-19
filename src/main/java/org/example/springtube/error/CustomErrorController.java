package org.example.springtube.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage;
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus httpStatus = HttpStatus.resolve(statusCode);
            if (httpStatus != null) {
                errorMessage = statusCode + " - " + httpStatus.getReasonPhrase();
            } else {
                errorMessage = "An unexpected error occurred. Please try again later.";
            }
        } else {
            errorMessage = "An unexpected error occurred. Please try again later.";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "custom_error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
