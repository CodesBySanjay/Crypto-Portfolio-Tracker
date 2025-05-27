package com.crypto.tracker.service;

import com.crypto.tracker.dto.JwtAuthResponse;
import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import com.crypto.tracker.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;
    private final AuthenticationManager auth;

    public String register(RegisterRequest r) {
        if (repo.existsByEmail(r.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        User u = new User();
        u.setName(r.getName());
        u.setEmail(r.getEmail());
        u.setPassword(encoder.encode(r.getPassword()));
        try {
            u.setRole(Role.valueOf(r.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Allowed values: USER, ADMIN");
        }
        repo.save(u);
        return "User registered";
    }

    public JwtAuthResponse login(LoginRequest r) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(r.getEmail(), r.getPassword()));
        User u = repo.findByEmail(r.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        String token = jwt.generateToken(u.getEmail(), u.getRole().name());
        return new JwtAuthResponse(token);
    }
}