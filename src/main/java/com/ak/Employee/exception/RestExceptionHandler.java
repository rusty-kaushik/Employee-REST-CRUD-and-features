package com.ak.Employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return buildResponseEntityForEmployeeExceptions(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EmployeeCreationFailedException.class})
    public ResponseEntity<Object> handleEmployeeCreationFailedException(EmployeeCreationFailedException ex) {
        return buildResponseEntityForEmployeeExceptions(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {EmployeeUpdateFailedException.class})
    public ResponseEntity<Object> handleEmployeeUpdateFailedException(EmployeeUpdateFailedException ex) {
        return buildResponseEntityForEmployeeExceptions(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {EmployeeDeletionFailedException.class})
    public ResponseEntity<Object> handleEmployeeDeletionFailedException(EmployeeDeletionFailedException ex) {
        return buildResponseEntityForEmployeeExceptions(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {CSVFileUploadFailedException.class})
    public ResponseEntity<Object> handleCSVFileUploadFailedException(CSVFileUploadFailedException ex) {
        return buildResponseEntityForEmployeeExceptions(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> buildResponseEntityForEmployeeExceptions(RuntimeException ex, HttpStatus status) {
        RestException restException = new RestException(
                ex.getMessage(),
                ex.getCause(),
                status
        );
        return new ResponseEntity<>(restException, status);
    }

}