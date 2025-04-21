package com.disease.disease_prediction_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // This handles GET requests to the root URL and loads the index.html form
    @GetMapping("/")
    public String showForm() {
        return "index"; // Renders index.html from templates
    }
}
