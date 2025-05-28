package com.crypto.tracker.controller;

import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import com.crypto.tracker.security.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SessionStore sessionStore;
    private final UserRepository repo;

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam("code") String code) {
        User user = sessionStore.getUser(code);
        if (user == null) throw new RuntimeException("Invalid or expired session");
        if (user.getRole() != Role.ADMIN) throw new RuntimeException("Access denied: Admins only");
        return repo.findAll();
    }

    // placeholder: this endpoint will call Sandhiya’s portfolio service later
    @GetMapping("/portfolio/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserPortfolio(@PathVariable Long id) {
        return "This will show portfolio for user ID: " + id;
    }
}