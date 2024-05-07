package org.example.springtube.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Loading {

    @GetMapping("/")
    public String loadingPage() {
        return "loading";
    }
}
