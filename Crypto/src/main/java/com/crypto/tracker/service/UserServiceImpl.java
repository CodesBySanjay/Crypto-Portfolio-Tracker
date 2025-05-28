package com.crypto.tracker.service;

import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.dto.SessionAuthResponse;
import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import com.crypto.tracker.security.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final SessionStore session;

    public String register(RegisterRequest r) {
        if (repo.existsByEmail(r.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User u = new User();
        u.setName(r.getName());
        u.setEmail(r.getEmail());
        u.setPassword(encoder.encode(r.getPassword()));

        try {
            u.setRole(Role.valueOf(r.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Allowed: USER, ADMIN");
        }

        if (u.getRole() == Role.USER) {
            u.setWalletBalance(BigDecimal.valueOf(10000));
        } else {
            u.setWalletBalance(null);
        }

        repo.save(u);
        return "User registered";
    }

    public SessionAuthResponse login(LoginRequest r) {
        User u = repo.findByEmail(r.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!encoder.matches(r.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String code = session.createSession(u);
        u.setSessionCode(code);
        repo.save(u);
        return new SessionAuthResponse(u.getEmail(), code);
    }
}