package com.crypto.tracker.service;

import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Test
    void loadUserByUsername_returnsUserDetails_whenUserExists() {
        UserRepository repo = mock(UserRepository.class);
        CustomUserDetailsService service = new CustomUserDetailsService(repo);

        User u = new User();
        u.setEmail("x@a.com");
        u.setPassword("pass");
        u.setRole(Role.USER);

        when(repo.findByEmail("x@a.com")).thenReturn(Optional.of(u));

        UserDetails details = service.loadUserByUsername("x@a.com");

        assertEquals("x@a.com", details.getUsername());
        assertEquals("pass", details.getPassword());
    }

    @Test
    void loadUserByUsername_throws_whenUserNotFound() {
        UserRepository repo = mock(UserRepository.class);
        CustomUserDetailsService service = new CustomUserDetailsService(repo);

        when(repo.findByEmail("none@a.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("none@a.com"));
    }
}