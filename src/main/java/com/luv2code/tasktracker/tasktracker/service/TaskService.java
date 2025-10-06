package com.luv2code.tasktracker.tasktracker.service;

import com.luv2code.tasktracker.tasktracker.dto.TaskRequestDTO;
import com.luv2code.tasktracker.tasktracker.dto.TaskResponseDTO;
import com.luv2code.tasktracker.tasktracker.entity.Task;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import com.luv2code.tasktracker.tasktracker.enums.Status;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.InvalidArgumentException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.LogicException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.TaskNotFoundException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.UserNotFoundException;
import com.luv2code.tasktracker.tasktracker.mapper.TaskMapper;
import com.luv2code.tasktracker.tasktracker.repositories.TaskRepository;
import com.luv2code.tasktracker.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {

        Task task = TaskMapper.fromDTO(taskRequestDTO);

        Optional<User> result = userRepository.findById(task.getCreatedBy().getId());
        User creator = null;
        if (result.isPresent()) {
            creator = result.get();
            task.setCreatedBy(creator);

            //Role-based permission check
            if (RoleName.ADMIN == creator.getRole().getName()) {
                // Admin case
                if (task.getAssignedTo() != null && task.getAssignedTo().getId() > 0) {
                    Optional<User> result1 = userRepository.findById(task.getAssignedTo().getId());
                    User assignee = null;
                    if (result1.isPresent()) {
                        assignee = result1.get();
                        task.setAssignedTo(assignee);
                    }
                } else {
                    // if assignedTo is null, assign to creator (admin themselves)
                    task.setAssignedTo(creator);
                }
            } else if (RoleName.USER == creator.getRole().getName()) {
                // USER case
                if (task.getAssignedTo() != null && task.getAssignedTo().getId() > 0) {
                    // user tries to assign to someone else
                    if (task.getAssignedTo().getId() != (creator.getId())) {
                        throw new LogicException("User cannot assign tasks to other users");
                    }
                }
                // assign task to self if already assigned to self or assigned to is null
                task.setAssignedTo(creator);
            } else {
                throw new LogicException("Unknown role: " + creator.getRole().toString());
            }

        } else {
            throw new UserNotFoundException("Did not find creator id of " + task.getCreatedBy().getId() + ". The creator must be provided.");
        }
        Task theTask = taskRepository.save(task);
        return TaskMapper.toDTO(theTask);
    }






    public List<TaskResponseDTO> findAll(Status status, Integer assignee_id) {
        // get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User authUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        List<Task> tasks;

        if (authUser.getRole().getName() == RoleName.USER) {
            int userId = authUser.getId();

            //  if user tries to request someone else's tasks
            if (assignee_id != null && !assignee_id.equals(userId)) {
                throw new LogicException("Users can only access their own tasks");
            }

            //  user can list their own tasks with optional status filter
            if (status != null) {
                tasks = taskRepository.findByStatusAndAssignedTo_Id(status, userId);
            } else {
                tasks = taskRepository.findByAssignedTo_Id(userId);
            }

        } else {
            //  Admin flow
            if (status != null && assignee_id != null) {
                tasks = taskRepository.findByStatusAndAssignedTo_Id(status, assignee_id);
            } else if (status != null) {
                tasks = taskRepository.findByStatus(status);
            } else if (assignee_id != null) {
                tasks = taskRepository.findByAssignedTo_Id(assignee_id);
            } else {
                tasks = taskRepository.findAll();
            }
        }

        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());

    }


    public TaskResponseDTO findById(int task_id) {
        // get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User authUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        // if not admin
        if (authUser.getRole().getName() == RoleName.USER) {
            throw new LogicException("Users are not allowed to fetch tasks by ID");
        }

        Optional<Task> result = taskRepository.findById(task_id);
        Task theTask = null;
        if (result.isPresent()) {
            theTask = result.get();
        } else {
            throw new TaskNotFoundException("Did not find task id of " + task_id);
        }
        return TaskMapper.toDTO(theTask);
    }


    public TaskResponseDTO updateTask(int task_id, TaskRequestDTO theTaskRequestDTO) {
        Optional<Task> result = taskRepository.findById(task_id);
        Task theTask = null;
        if (result.isPresent()) {
            theTask = result.get();
            if (theTaskRequestDTO.getTitle() != null) {
                theTask.setTitle(theTaskRequestDTO.getTitle());
            }
            if (theTaskRequestDTO.getDescription() != null) {
                theTask.setDescription(theTaskRequestDTO.getDescription());
            }
            if (theTaskRequestDTO.getStatus() != null) {
                boolean isValid = Arrays.stream(Status.values()).anyMatch(status -> status == theTaskRequestDTO.getStatus());
                if (!isValid) {
                    throw new InvalidArgumentException("Invalid status value" + theTaskRequestDTO.getStatus());
                }
                Optional<Task> taskResult = taskRepository.findById(task_id);
                if (taskResult.isPresent()) {
                    theTask.setStatus(theTaskRequestDTO.getStatus());
                }
            }
            if (theTaskRequestDTO.getAssignedTo() != null) {
                Optional<User> assignee = userRepository.findById(theTaskRequestDTO.getAssignedTo().getId());
                if (assignee.isPresent()) {
                    theTask.setAssignedTo(theTaskRequestDTO.getAssignedTo());
                } else {
                    throw new LogicException("Assignee was not found ");
                }
            }

        } else {
            throw new TaskNotFoundException("Did not find task id of " + task_id);
        }
        Task savedTask = taskRepository.save(theTask);
        return TaskMapper.toDTO(savedTask);
    }


    public TaskResponseDTO updateTaskStatus(int task_id, Status newStatus) {

        if (newStatus == null) {
            throw new InvalidArgumentException("Status cannot be null");
        }
        // validate newStatus against enum values
        boolean isValid = Arrays.stream(Status.values()).anyMatch(status -> status == newStatus);

        if (!isValid) {
            throw new InvalidArgumentException("Invalid status value" + newStatus);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User authUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));


        Optional<Task> result = taskRepository.findById(task_id);
        Task theTask = null;
        if (result.isPresent()) {
            theTask = result.get();
            theTask.setStatus(newStatus);
        } else {
            throw new TaskNotFoundException("Did not find task id of " + task_id);
        }

// check permissions
        if (authUser.getRole().getName() == RoleName.USER) {
            if (theTask.getAssignedTo() == null || theTask.getAssignedTo().getId() != authUser.getId()) {
                throw new LogicException("Users are only allowed to update the status of their own tasks");
            }
        }


        Task savedTask = taskRepository.save(theTask);
        return TaskMapper.toDTO(savedTask);
    }


    public String deleteTask(int task_id){
        Optional<Task> result = taskRepository.findById(task_id);
        Task theTask = null;
        if (result.isPresent()) {
            taskRepository.deleteById(task_id);
        } else {
            throw new TaskNotFoundException("Did not find task id of " + task_id);
        }
    return "Deleted task id : " + task_id;
    }

}

