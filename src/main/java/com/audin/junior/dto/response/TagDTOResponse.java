package com.audin.junior.dto.response;

import java.time.LocalDateTime;

public record TagDTOResponse(Integer id, Integer user_id, String name, LocalDateTime createdAt) {

}
