package com.luv2code.tasktracker.tasktracker.exceptionhandlers.models;

public class TaskErrorResponse {

    // define fields
    private int status;
    private String message;
    private long timeStamp;

    // define constructors
    public TaskErrorResponse() {
    }
    public TaskErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    // define getters and setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
