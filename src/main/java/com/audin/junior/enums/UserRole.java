package com.audin.junior.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("User"),
    ADMIN("Admin");

    private final String label;
}
