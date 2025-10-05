package com.luv2code.tasktracker.tasktracker.controller;

import com.luv2code.tasktracker.tasktracker.dto.TaskDTO;
import com.luv2code.tasktracker.tasktracker.entity.Task;
import com.luv2code.tasktracker.tasktracker.enums.Status;
import com.luv2code.tasktracker.tasktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TaskDTO createTask(@RequestBody Task theTask) {
        return taskService.createTask(theTask);
    }

    @GetMapping("/tasks")
    public List<TaskDTO> getTasks(
            @RequestParam(required = false)Status status,
            @RequestParam(required = false)Integer assignee_id) {
        return taskService.findAll(status,assignee_id);
    }

    @GetMapping("/tasks/{task_id}")
    public TaskDTO getTask(@PathVariable int task_id) {
        return taskService.findById(task_id);
    }

    @PutMapping("/tasks/{task_id}")
    public TaskDTO updateTask(
            @PathVariable int task_id,
            @RequestBody Task newTask) {
        return taskService.updateTask(task_id, newTask);
    }

    @PatchMapping("/tasks/{task_id}/status")
    public TaskDTO updateTaskStatus(
            @PathVariable int task_id,
            @RequestBody Task newTask) {
        return taskService.updateTaskStatus(task_id, newTask.getStatus());
    }


    @DeleteMapping("/tasks/{task_id}")
    public String deleteTask (@PathVariable int task_id){
        return taskService.deleteTask(task_id);
    }
}









