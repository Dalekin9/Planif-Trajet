package com.planifcarbon.backend.exceptions;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The controller for exception handling.
 */
@ExcludeFromJacocoGeneratedReport
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Exception handler method that catches exceptions of type Exception
     * thrown by any method within the controller and returns a ResponseEntity
     * object containing the exception message and HTTP status code 400 (Bad Request).
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity object containing the exception message and HTTP status code 400 (Bad Request)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
