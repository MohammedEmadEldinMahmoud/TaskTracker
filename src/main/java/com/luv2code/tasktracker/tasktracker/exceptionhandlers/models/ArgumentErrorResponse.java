package com.luv2code.tasktracker.tasktracker.exceptionhandlers.models;

public class ArgumentErrorResponse {

    // define fields
    private String message;
    private long timeStamp;

    // define constructors
    public ArgumentErrorResponse() {
    }
    public ArgumentErrorResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    // define getters and setters


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
