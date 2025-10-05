package com.luv2code.tasktracker.tasktracker.exceptionhandlers.handlers;

import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.InvalidArgumentException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.models.ArgumentErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidArgumentExceptionHandler {

    // catch task not found exceptions
    @ExceptionHandler
    public ResponseEntity<ArgumentErrorResponse> handleInvalidArgument(InvalidArgumentException exc) {

        // create a TaskErrorResponse and set its values
        ArgumentErrorResponse error = new ArgumentErrorResponse();
       error.setMessage(exc.getMessage());
       error.setTimeStamp(System.currentTimeMillis());

       // return ResponseEntity
       return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }












}
