package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.dto.response.TaskDTOResponse;
import com.audin.junior.entity.Tag;
import com.audin.junior.entity.Task;
import com.audin.junior.enums.TaskStatus;
import com.audin.junior.mapper.TaskMapper;
import com.audin.junior.service.TaskService;

import lombok.AllArgsConstructor;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping(path = "tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public @ResponseBody TaskDTOResponse createTask(@RequestBody TaskDTORequest dtoRequest) {

        Task task = taskService.createTask(dtoRequest);
        TaskDTOResponse entity = taskMapper.toDto(task);
        return entity;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}")
    public @ResponseBody TaskDTOResponse toogleStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String statusStr = body.get("status");
        TaskStatus status = TaskStatus.valueOf(statusStr);
        System.out.println("STATUS : " + status);
        Task task = taskService.findById(id);
        task = taskService.toogleSatus(task, status);
        return ResponseEntity.ok(taskMapper.toDto(task)).getBody();
    }

    @PatchMapping("/{id}/toogleArchived")
    public @ResponseBody TaskDTOResponse toogleArchived(@PathVariable Integer id) {
        Task task = taskService.findById(id);
        task = taskService.toogleArchived(task);
        return ResponseEntity.ok(taskMapper.toDto(task)).getBody();
    }

    @GetMapping
    public @ResponseBody List<TaskDTOResponse> findAllByUser() {
        List<Task> tasks = taskService.findAllByUser();
        return taskMapper.toDto(tasks);
    }

    @GetMapping("/{idOrSlug}")
    public @ResponseBody TaskDTOResponse findByIdOrSlug(@PathVariable String idOrSlug) {
        Task task;
        if (idOrSlug.matches("^[0-9]+$")) {
            task = taskService.findById(Integer.valueOf(idOrSlug));
        } else {
            task = taskService.findBySlug(idOrSlug);
        }
        TaskDTOResponse entity = taskMapper.toDto(task);
        return entity;
    }

}
