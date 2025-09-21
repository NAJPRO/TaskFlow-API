package com.audin.junior.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {
    TODO("À faire"),
    IN_PROGRESS("En cours"),
    REVIEW("En revue"),
    DONE("Terminée"),
    ARCHIVED("Archivé"),
    CANCELLED("Annulée");
    
    private final String label;
}