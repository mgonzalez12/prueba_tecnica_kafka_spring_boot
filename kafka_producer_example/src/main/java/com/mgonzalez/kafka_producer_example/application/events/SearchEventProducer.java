package com.mgonzalez.kafka_producer_example.application.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgonzalez.kafka_producer_example.domian.dto.FullSearchResponse;
import com.mgonzalez.kafka_producer_example.domian.dto.SearchRequest;
import com.mgonzalez.kafka_producer_example.domian.dto.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class SearchEventProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public SearchResponse sendSearchRequest(FullSearchResponse fullSearchRequest) throws JsonProcessingException {
        String value = objectMapper.writeValueAsString(fullSearchRequest);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.sendDefault(fullSearchRequest.getSearch().getHotelId(), value);
        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                handleFailure(fullSearchRequest.getSearch().getHotelId(), fullSearchRequest, throwable);
            }
        });
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setSearchId(fullSearchRequest.getSearchId()); // Utiliza el ID existente
        return searchResponse;
    }

    // Implement the handleFailure method
    private void handleFailure(String key, FullSearchResponse value, Throwable throwable) {
        // Handle the failure appropriately, e.g., log the error, retry, or notify a monitoring system
        System.out.println("Error sending message: " + throwable.getMessage());
        // Add your specific error handling logic here
    }
}
