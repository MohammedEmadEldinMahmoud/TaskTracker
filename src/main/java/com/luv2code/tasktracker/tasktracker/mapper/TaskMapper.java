package com.luv2code.tasktracker.tasktracker.mapper;

import com.luv2code.tasktracker.tasktracker.dto.TaskRequestDTO;
import com.luv2code.tasktracker.tasktracker.dto.TaskResponseDTO;
import com.luv2code.tasktracker.tasktracker.entity.Task;

public class TaskMapper {

    public static TaskResponseDTO toDTO(Task task){
            TaskResponseDTO dto = new TaskResponseDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());

        if (task.getCreatedBy() != null) {
            dto.setCreatedBy(task.getCreatedBy());
        }
        if (task.getAssignedTo() != null) {
            dto.setAssignedTo(task.getAssignedTo());
            }

            return dto;
        }


    public static Task fromDTO(TaskRequestDTO dto) {
        Task task = new Task();
// set the task id to 0 just in case they put an id in json
        // to enforce the creation of a new task not an update
        task.setId(0);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        if (dto.getCreatedBy() != null) {
            task.setCreatedBy(dto.getCreatedBy());
        }
        if (dto.getAssignedTo() != null) {
            task.setAssignedTo(dto.getAssignedTo());
        }

        return task;
    }


    }
