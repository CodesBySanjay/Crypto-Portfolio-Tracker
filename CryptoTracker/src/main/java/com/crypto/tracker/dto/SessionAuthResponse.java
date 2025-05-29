package com.crypto.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionAuthResponse {
    private String email;
    private String sessionCode;
}