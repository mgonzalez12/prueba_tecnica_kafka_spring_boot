package com.mgonzalez.kafka_producer_example.infrastructure.adapter.exceptions;

import java.util.List;

public class ErrorResponse {
    private List<String> errorMessages;

    public ErrorResponse(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    // Getter for errorMessages
}