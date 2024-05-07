package org.example.springtube.controllers;

import org.example.springtube.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ConfirmController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/confirm/{code}")
    public String confirmUser(@PathVariable("code") String code) {
        boolean confirmed = signUpService.confirmUser(code);
        if (!confirmed) {
            return "confirmation_error";
        } else {
            return "confirmation_success";
        }
    }
}