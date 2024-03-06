package com.mgonzalez.kafka_producer_example.infrastructure.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgonzalez.kafka_producer_example.application.events.KafkaProducerConsultation;
import com.mgonzalez.kafka_producer_example.application.events.SearchEventProducer;
import com.mgonzalez.kafka_producer_example.domian.constant.Validations;
import com.mgonzalez.kafka_producer_example.domian.dto.FullSearchResponse;
import com.mgonzalez.kafka_producer_example.domian.dto.SearchRequest;
import com.mgonzalez.kafka_producer_example.domian.dto.SearchResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class SearchEventsController {
    @Autowired
    SearchEventProducer searchEventProducer;
    @Autowired
    Validations validations;
    @Autowired
    private KafkaProducerConsultation kafkaProducerConsultation;

    @PostMapping("/api/searchevent")
    public ResponseEntity<SearchResponse> postSearchEvent(@RequestBody @Valid SearchRequest searchRequest, BindingResult result)
            throws JsonProcessingException {
        if (result.hasErrors()) { return (ResponseEntity<SearchResponse>) validations.validation(result);}
        log.info("Received SearchRequest: {}", searchRequest); log.info("Invoking Kafka Producer: Begin");
        // Crear un objeto FullSearchResponse y asignar los valores del SearchRequest
        FullSearchResponse fullSearchRequest = new FullSearchResponse();
        fullSearchRequest.setSearchId(UUID.randomUUID().toString()); fullSearchRequest.setSearch(searchRequest);
        log.info("Invoking Kafka Producer : {}  Ends", fullSearchRequest);
        // Generar el searchId y enviarlo al productor
        SearchResponse searchResponse = searchEventProducer.sendSearchRequest(fullSearchRequest);
        log.info("Invoking Kafka Producer: Ends");
        return ResponseEntity.status(HttpStatus.CREATED).body(searchResponse);
    }
    @GetMapping("/count")
    public ResponseEntity<String> count(@RequestParam String searchId) throws ExecutionException, InterruptedException {
        // Envía el searchId al topic "requests" de Kafka y obtén un CompletableFuture
        CompletableFuture<String> future = kafkaProducerConsultation.send("requests", searchId);
        log.info("Kafka Producer response : {}", future.get());
        // Espera a que el CompletableFuture se complete y obtén la respuesta
        String response = future.get();
        // Devuelve la respuesta al cliente
        return ResponseEntity.ok(response);
    }
}
