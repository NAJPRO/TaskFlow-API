package com.audin.junior.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.audin.junior.dto.request.TagDTORequest;
import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.entity.Category;
import com.audin.junior.entity.Tag;
import com.audin.junior.entity.Task;
import com.audin.junior.entity.User;
import com.audin.junior.enums.TaskStatus;
import com.audin.junior.mapper.TaskMapper;
import com.audin.junior.repository.TaskRepository;
import com.audin.junior.service.CategoryService;
import com.audin.junior.service.TagService;
import com.audin.junior.service.TaskService;
import com.audin.junior.utils.AuthUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final AuthUtils authUtils;

    @Override
    public Task createTask(TaskDTORequest taskDTO) {
        User user = authUtils.getCurrentUser();
        Category category = categoryService.findByIdAndUser(taskDTO.category_id());
        List<Tag> tags = taskDTO.tags().stream().map(
                tag -> this.tagService.save(new TagDTORequest(tag))).toList();
       // tagService.findAllByIdsAndUser(taskDTO.tag_ids());
        Task task = taskMapper.toEntity(taskDTO, user, category, tags);
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public Task toogleArchived(Task task) {
        task.setArchived(!task.isArchived());
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Integer id) {
        User user = authUtils.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow();
        taskRepository.delete(task);
    }

    @Override
    public List<Task> findAllByStatus(TaskStatus status) {
        User user = authUtils.getCurrentUser();

        List<Task> tasks = taskRepository.findAllByUserAndStatus(user, status);
        return tasks;
    }

    @Override
    public List<Task> findAllByUser() {
        User user = authUtils.getCurrentUser();

        List<Task> tasks = taskRepository.findAllByUser(user);
        return tasks;
    }

    @Override
    public Task findById(Integer id) {
        User user = authUtils.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow();
        return task;
    }

    @Override
    public Task findBySlug(String slug) {
        User user = authUtils.getCurrentUser();

        Task task = taskRepository.findBySlugAndUser(slug, user).orElseThrow();
        return task;
    }

    @Override
    public Task toogleSatus(Task task, TaskStatus status) {
        task.setStatus(status);
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public Task updateTask(Integer id, TaskDTORequest taskDTO) {
        // TODO Auto-generated method stub
        return null;
    }

}
