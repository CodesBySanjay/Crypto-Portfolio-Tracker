package com.crypto.tracker.service;

import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.dto.JwtAuthResponse;

public interface UserService {
    String register(RegisterRequest request);
    JwtAuthResponse login(LoginRequest request);
}