package com.ak.Employee.exception;

public class EmployeeUpdateFailedException extends RuntimeException{

    public EmployeeUpdateFailedException(String message){
        super(message);
    }

    public EmployeeUpdateFailedException(String message, Throwable cause){
        super(message,cause);
    }

}
