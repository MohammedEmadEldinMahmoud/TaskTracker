package com.luv2code.tasktracker.tasktracker.exceptionhandlers.handlers;

import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.UserNotFoundException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.models.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotFoundExceptionHandler {

    // catch user not found exceptions
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserNotFound(UserNotFoundException exc) {

        // create a UserErrorResponse and set its values
       UserErrorResponse error = new UserErrorResponse();
       error.setStatus(HttpStatus.NOT_FOUND.value());
       error.setMessage(exc.getMessage());
       error.setTimeStamp(System.currentTimeMillis());

       // return ResponseEntity
       return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }




    // Catch any other type of exceptions (like invalid entries,... )
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exc) {

        // create a UserErrorResponse and set its values
        UserErrorResponse error = new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }







}
