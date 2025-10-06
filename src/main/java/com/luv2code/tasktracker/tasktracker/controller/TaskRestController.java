package com.luv2code.tasktracker.tasktracker.controller;

import com.luv2code.tasktracker.tasktracker.dto.TaskRequestDTO;
import com.luv2code.tasktracker.tasktracker.dto.TaskResponseDTO;
import com.luv2code.tasktracker.tasktracker.entity.Task;
import com.luv2code.tasktracker.tasktracker.enums.Status;
import com.luv2code.tasktracker.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
public class TaskRestController {

    private TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    // add mapping for GET /users/{userId}
    @PostMapping("/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO theTaskRequestDTO) {
        return taskService.createTask(theTaskRequestDTO);
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getTasks(
            @RequestParam(required = false)Status status,
            @RequestParam(required = false)Integer assignee_id) {
        return taskService.findAll(status,assignee_id);
    }

    @GetMapping("/tasks/{task_id}")
    public TaskResponseDTO getTask(@PathVariable int task_id) {
        return taskService.findById(task_id);
    }

    @PutMapping("/tasks/{task_id}")
    public TaskResponseDTO updateTask(
            @PathVariable int task_id,
            @RequestBody TaskRequestDTO theTaskRequestDTO) {
        return taskService.updateTask(task_id, theTaskRequestDTO);
    }

    @PatchMapping("/tasks/{task_id}/status")
    public TaskResponseDTO updateTaskStatus(
            @PathVariable int task_id,
            @RequestParam Status status) {
        return taskService.updateTaskStatus(task_id, status);
    }


    @DeleteMapping("/tasks/{task_id}")
    public String deleteTask (@PathVariable int task_id){
        return taskService.deleteTask(task_id);
    }
}









