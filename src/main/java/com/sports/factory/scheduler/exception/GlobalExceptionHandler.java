package com.sports.factory.scheduler.exception;

import com.sports.factory.scheduler.model.api.Error;
import com.sports.factory.scheduler.model.api.ErrorResponse;
import com.sports.factory.scheduler.model.api.ErrorResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        List<ErrorResults> errorResultsList = new ArrayList<>();
        ErrorResults errorResults = new ErrorResults();
        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setCode("10000");
        error.setMessage("Provider Timeout");
        error.setDetails(ex.getLocalizedMessage());
        errorList.add(error);
        errorResults.setError(errorList);
        errorResultsList.add(errorResults);
        errorResponse.setErrorList(errorResultsList);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        List<ErrorResults> errorResultsList = new ArrayList<>();
        ErrorResults errorResults = new ErrorResults();
        List<Error> errorList = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
       for (ConstraintViolation<?> constraintViolation : constraintViolations) {
           Error error = new Error();
           error.setCode("1201");
           error.setMessage("Invalid input request");
           error.setDetails(constraintViolation.getPropertyPath().toString()+" - "+constraintViolation.getMessage());
           errorList.add(error);
       }
        errorResults.setError(errorList);
        errorResultsList.add(errorResults);
        errorResponse.setErrorList(errorResultsList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        List<ErrorResults> errorResultsList = new ArrayList<>();
        ErrorResults errorResults = new ErrorResults();
        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setCode("10001");
        error.setMessage("Unexpected error");
        error.setDetails(ex.getMessage());
        errorList.add(error);
        errorResults.setError(errorList);
        errorResultsList.add(errorResults);
        errorResponse.setErrorList(errorResultsList);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
