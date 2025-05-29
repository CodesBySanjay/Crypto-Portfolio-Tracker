package com.crypto.tracker.security;

import com.crypto.tracker.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SessionStore {

    private final Map<String, User> store = new HashMap<>();

    public String create(User user) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        store.put(code, user);
        return code;
    }

    public User get(String code) {
        return store.get(code);
    }

    public void remove(String code) {
        store.remove(code);
    }
}