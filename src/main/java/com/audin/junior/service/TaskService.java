package com.audin.junior.service;

import java.util.List;

import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.entity.Task;
import com.audin.junior.enums.TaskStatus;

public interface TaskService {
    Task createTask(TaskDTORequest taskDTO);
    Task findById(Integer id);
    Task findBySlug(String slug);
    Task updateTask(Integer id, TaskDTORequest taskDTO);
    void deleteTask(Integer id);

    List<Task> findAllByUser();
    List<Task> findAllByStatus(TaskStatus status);
}
