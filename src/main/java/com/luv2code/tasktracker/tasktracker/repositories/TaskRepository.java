package com.luv2code.tasktracker.tasktracker.repositories;

import com.luv2code.tasktracker.tasktracker.entity.Task;
import com.luv2code.tasktracker.tasktracker.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByStatus(Status status);

    List<Task> findByAssignedTo_Id(int assignee_Id);

    List<Task> findByStatusAndAssignedTo_Id(Status status, int assignee_Id);
}
