package com.luv2code.tasktracker.tasktracker.mapper;

import com.luv2code.tasktracker.tasktracker.dto.TaskDTO;
import com.luv2code.tasktracker.tasktracker.entity.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task){
            TaskDTO dto = new TaskDTO();
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
    }
