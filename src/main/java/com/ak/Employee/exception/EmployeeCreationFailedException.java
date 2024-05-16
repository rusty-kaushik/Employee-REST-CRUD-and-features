package com.ak.Employee.exception;

public class EmployeeCreationFailedException extends RuntimeException{

    public EmployeeCreationFailedException(String message){
        super(message);
    }

    public EmployeeCreationFailedException(String message, Throwable cause){
        super(message,cause);
    }

}
