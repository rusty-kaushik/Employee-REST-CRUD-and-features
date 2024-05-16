package com.ak.Employee.exception;

public class CSVFileUploadFailedException extends RuntimeException{

    public CSVFileUploadFailedException(String message){
        super(message);
    }

    public CSVFileUploadFailedException(String message, Throwable cause){
        super(message,cause);
    }

}
