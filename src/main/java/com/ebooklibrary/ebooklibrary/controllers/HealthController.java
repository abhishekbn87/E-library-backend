package com.ebooklibrary.ebooklibrary.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String ping() {
        return "Hello & Welcome to Order Service !!!";
    }
}