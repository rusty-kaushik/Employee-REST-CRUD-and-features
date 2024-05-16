package com.ak.Employee.exception;

public class EmployeeDeletionFailedException extends RuntimeException{

    public EmployeeDeletionFailedException(String message){
        super(message);
    }

    public EmployeeDeletionFailedException(String message, Throwable cause){
        super(message,cause);
    }

}
