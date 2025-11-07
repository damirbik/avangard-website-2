// src/main/java/ru/avangard/website/controller/AuthController.java
package ru.avangard.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avangard.website.entity.Admin;
import ru.avangard.website.service.AuthService;

@RestController
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.login, request.password);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Токен не предоставлен");
        }

        String token = authHeader.substring(7); // убираем "Bearer "

        if (authService.validateToken(token)) {
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.status(401).body("Невалидный токен");
        }
    }

    static class LoginRequest {
        public String login;
        public String password;
    }

    static class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}