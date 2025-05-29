package com.crypto.tracker.controller;

import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import com.crypto.tracker.security.SessionStore;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.crypto.tracker.exception.AccessDeniedException;
import com.crypto.tracker.exception.InvalidSessionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name= "Admin", description = "Only for Admins")
public class AdminController {

    private final UserRepository repo;
    private final SessionStore sessionStore;

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam("code") String code) {
        User user = sessionStore.get(code);
        if (user == null) throw new InvalidSessionException("Invalid or expired session");
        if (user.getRole() != Role.ADMIN) throw new AccessDeniedException("Admins only");
        return repo.findAll();
    }
}