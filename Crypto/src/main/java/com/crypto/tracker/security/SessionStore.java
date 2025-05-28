package com.crypto.tracker.security;

import com.crypto.tracker.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SessionStore {

    private final Map<String, User> store = new HashMap<>();

    public String createSession(User user) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        store.put(code, user);
        return code;
    }

    public User getUser(String code) {
        return store.get(code);
    }

    public void invalidate(String code) {
        store.remove(code);
    }
}