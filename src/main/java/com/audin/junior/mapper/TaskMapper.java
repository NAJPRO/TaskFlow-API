package com.audin.junior.mapper;

import java.util.List;

import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.dto.response.TaskDTOResponse;
import com.audin.junior.entity.Category;
import com.audin.junior.entity.Tag;
import com.audin.junior.entity.Task;
import com.audin.junior.entity.User;

public interface TaskMapper {
    TaskDTOResponse toDto(Task entity);
    List<TaskDTOResponse> toDto(List<Task> entities);
    Task toEntity(TaskDTORequest dto, User user, Category category, List<Tag> tags);

}
