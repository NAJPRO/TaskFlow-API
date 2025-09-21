package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.TaskDTORequest;
import com.audin.junior.dto.response.TaskDTOResponse;
import com.audin.junior.entity.Task;
import com.audin.junior.mapper.TaskMapper;
import com.audin.junior.service.TaskService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public void deleteTask(@RequestParam Integer id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public @ResponseBody List<TaskDTOResponse> findAllByUser() {
        List<Task> tasks = taskService.findAllByUser();
        return taskMapper.toDto(tasks);
    }

    @GetMapping("/{idOrSlug}")
    public @ResponseBody TaskDTOResponse findByIdOrSlug(@PathVariable String idOrSlug) {
        Task task;
        if(idOrSlug.matches("^[0-9]+$")){
            task = taskService.findById(Integer.valueOf(idOrSlug));
        }else{
            task = taskService.findBySlug(idOrSlug);
        }
        TaskDTOResponse entity = taskMapper.toDto(task);        
        return entity;
    }
    
    
    
    
}
