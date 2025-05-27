package com.crypto.tracker.controller;

import com.crypto.tracker.dto.JwtAuthResponse;
import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/login")
    public JwtAuthResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }
}