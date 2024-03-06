package com.mgonzalez.kafka_producer_example.infrastructure.rest.advice;

import com.mgonzalez.kafka_producer_example.infrastructure.adapter.exceptions.SearchException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(SearchException.class)
    public ResponseEntity<String> handleEmptyInput(SearchException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

    @ExceptionHandler(ExecutionControl.UserException.class)
    public ResponseEntity<String> handleEmptyInput(ExecutionControl.UserException emptyInputException){
        //  return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
        return  null;
    }
}
