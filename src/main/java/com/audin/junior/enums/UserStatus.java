package com.audin.junior.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DELETED("Deleted");

    private final String label;
}
