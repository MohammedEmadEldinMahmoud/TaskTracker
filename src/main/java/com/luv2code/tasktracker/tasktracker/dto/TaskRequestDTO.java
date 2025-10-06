package com.luv2code.tasktracker.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.enums.Status;
import jakarta.validation.constraints.NotNull;


@JsonInclude(JsonInclude.Include.NON_NULL) // hide nulls in JSON responses
public class TaskRequestDTO {

    @NotNull(message = "Title is required")
    private String title;
    private String description;
    @NotNull(message = "Status is required")
    private Status status;
    @NotNull(message = "CreatedBy must not be null")
    private User createdBy;
    private User assignedTo;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO( String title, String description, Status status, User createdBy, User assignedTo) {

        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
