package com.audin.junior.dto.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.audin.junior.enums.TaskPriority;
import com.audin.junior.enums.TaskStatus;

public record TaskDTOResponse(
        Integer id,
        Integer user_id,
        String title,
        TaskStatus status,
        String description,
        TaskPriority priority,
        Instant endAt,
        boolean isArchived,
        CategoryDTOResponse category,
        LocalDateTime createdAt,
        List<TagDTOResponse> tags) {

}
