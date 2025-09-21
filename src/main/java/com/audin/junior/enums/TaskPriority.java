package com.audin.junior.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPriority {
    LOW("Faible"),
    MEDIUM("Moyenne"),
    HIGH("Haute"),
    URGENT("Urgente");
    
    private final String label;
}