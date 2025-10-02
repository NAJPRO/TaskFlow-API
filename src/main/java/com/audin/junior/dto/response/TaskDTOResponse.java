package com.audin.junior.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.audin.junior.enums.TaskPriority;

public record TaskDTOResponse(
        Integer id,
        Integer user_id,
        String title,
        String description,
        TaskPriority priority,
        Instant endAt,
        CategoryDTOResponse category,
        LocalDateTime createdAt,
        List<TagDTOResponse> tags) {

}
