package com.audin.junior.dto.response;

import java.time.LocalDateTime;

public record CategoryDTOResponse(Integer id, String name, LocalDateTime createdAt) {}

