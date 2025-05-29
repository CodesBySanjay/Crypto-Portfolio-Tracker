package com.crypto.tracker.controller;

import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.dto.SessionAuthResponse;
import com.crypto.tracker.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name= "User", description = " User - Login")
public class AuthController {

    private final UserService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/login")
    public SessionAuthResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }
}