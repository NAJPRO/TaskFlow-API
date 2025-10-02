package com.audin.junior.dto.request;

import java.util.List;

import com.audin.junior.enums.TaskPriority;

public record TaskDTORequest(
    String title,
    String description,
    TaskPriority priority,
    Integer category_id,
    String endAt,
    List<String> tags

    // List<Integer> tag_ids
) {}
