package com.crypto.tracker.service;

import com.crypto.tracker.dto.LoginRequest;
import com.crypto.tracker.dto.RegisterRequest;
import com.crypto.tracker.dto.SessionAuthResponse;
import com.crypto.tracker.model.Role;
import com.crypto.tracker.model.User;
import com.crypto.tracker.repository.UserRepository;
import com.crypto.tracker.security.SessionStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SessionStore session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_success() {
        RegisterRequest req = new RegisterRequest();
        req.setName("Alice");
        req.setEmail("alice@example.com");
        req.setPassword("password123");
        req.setRole("USER");

        when(repo.existsByEmail(req.getEmail())).thenReturn(false);
        when(encoder.encode(req.getPassword())).thenReturn("encodedPassword");

        String result = userService.register(req);

        assertEquals("User registered", result);
        verify(repo, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_duplicateEmail_throwsException() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("bob@example.com");

        when(repo.existsByEmail(req.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.register(req));
        assertEquals("Email already registered", ex.getMessage());
    }

    @Test
    void testRegister_invalidRole_throwsException() {
        RegisterRequest req = new RegisterRequest();
        req.setName("Test");
        req.setEmail("test@example.com");
        req.setPassword("pass");
        req.setRole("INVALID");

        when(repo.existsByEmail(req.getEmail())).thenReturn(false);
        when(encoder.encode(any())).thenReturn("pass");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.register(req));
        assertEquals("Invalid role. Allowed: USER, ADMIN", ex.getMessage());
    }

    @Test
    void testLogin_success() {
        LoginRequest req = new LoginRequest();
        req.setEmail("alice@example.com");
        req.setPassword("password123");

        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword("encodedPassword");

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.of(u));
        when(encoder.matches(req.getPassword(), u.getPassword())).thenReturn(true);
        when(session.create(u)).thenReturn("sessionCode");

        SessionAuthResponse response = userService.login(req);

        assertNotNull(response);
        assertEquals("alice@example.com", response.getEmail());
        assertEquals("sessionCode", response.getSessionCode());
        verify(repo, times(1)).save(u);
    }

    @Test
    void testLogin_invalidEmail_throwsException() {
        LoginRequest req = new LoginRequest();
        req.setEmail("notfound@example.com");

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.login(req));
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    void testLogin_invalidPassword_throwsException() {
        LoginRequest req = new LoginRequest();
        req.setEmail("bob@example.com");
        req.setPassword("wrongpass");

        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword("encodedPassword");

        when(repo.findByEmail(req.getEmail())).thenReturn(Optional.of(u));
        when(encoder.matches(req.getPassword(), u.getPassword())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.login(req));
        assertEquals("Invalid password", ex.getMessage());
    }
}
