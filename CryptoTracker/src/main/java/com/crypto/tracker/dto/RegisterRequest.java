package com.crypto.tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Pattern(
            regexp = "USER|ADMIN",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Role must be USER or ADMIN"
    )
    private String role;
}