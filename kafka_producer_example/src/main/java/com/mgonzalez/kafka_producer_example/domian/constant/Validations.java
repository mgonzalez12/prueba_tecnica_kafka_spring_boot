package com.mgonzalez.kafka_producer_example.domian.constant;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.stream.Collectors;
@Component
public class Validations {

    public ResponseEntity<?> validation(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage()
                    ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
