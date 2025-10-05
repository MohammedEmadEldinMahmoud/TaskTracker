package com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions;


public class LogicException extends RuntimeException{
    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }


}
