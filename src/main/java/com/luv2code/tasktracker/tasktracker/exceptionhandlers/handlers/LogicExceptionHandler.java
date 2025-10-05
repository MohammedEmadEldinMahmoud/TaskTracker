package com.luv2code.tasktracker.tasktracker.exceptionhandlers.handlers;

import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.LogicException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.TaskNotFoundException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.models.LogicErrorResponse;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.models.TaskErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LogicExceptionHandler {

    // catch task not found exceptions
    @ExceptionHandler
    public ResponseEntity<LogicErrorResponse> handleLogic(LogicException exc) {

        // create a TaskErrorResponse and set its values
       LogicErrorResponse error = new LogicErrorResponse();
       error.setMessage(exc.getMessage());
       error.setTimeStamp(System.currentTimeMillis());

       // return ResponseEntity
       return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }












}
