package com.audin.junior.mapper.Impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.dto.response.CategoryDTOResponse;
import com.audin.junior.dto.response.TagDTOResponse;
import com.audin.junior.dto.response.TaskDTOResponse;
import com.audin.junior.entity.Category;
import com.audin.junior.entity.Tag;
import com.audin.junior.entity.Task;
import com.audin.junior.entity.User;
import com.audin.junior.mapper.CategoryMapper;
import com.audin.junior.mapper.TagMapper;
import com.audin.junior.mapper.TaskMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TaskMapperImpl implements TaskMapper {
    private TagMapper tagMapper;
    private CategoryMapper categoryMapper;

    @Override
    public TaskDTOResponse toDto(Task entity) {
        List<TagDTOResponse> tags = tagMapper.toDto(entity.getTags());
        CategoryDTOResponse category = categoryMapper.toDto(entity.getCategory());
        TaskDTOResponse task = new TaskDTOResponse(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getStatus(),
                entity.getDescription(),
                entity.getPriority(),
                entity.getEndAt(),
                entity.isArchived(),
                category,
                entity.getCreatedAt(),
                tags);
        return task;
    }

    @Override
    public List<TaskDTOResponse> toDto(List<Task> entities) {
        List<TaskDTOResponse> taskResponses = new ArrayList<TaskDTOResponse>();
        for (Task task : entities) {
            taskResponses.add(this.toDto(task));
        }
        return taskResponses;
    }

    @Override
    public Task toEntity(TaskDTORequest dto, User user, Category category, List<Tag> tags) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        task.setUser(user);
        task.setEndAt(dto.endAt() != null ? Instant.parse(dto.endAt()) : null);
        task.setCategory(category);
        task.setTags(tags);
        return task;
    }

}
