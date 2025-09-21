package com.audin.junior.dto.response;

import java.time.LocalDateTime;

public record UserDTOResponse(
    Integer id,
    String pseudo,
    String email,
    String name,
    LocalDateTime emailVerifiedAt,
    String accessToken
) {}
