package com.example.employeemanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityDemoController {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome to java-techie online first batch";
    }

    @GetMapping("/text")
    public String greeting(){
        return "happy to see you here";
    }

    @GetMapping("/nonSecure")
    public String nonSecure(){
        return "welcome to home page";
    }
}
