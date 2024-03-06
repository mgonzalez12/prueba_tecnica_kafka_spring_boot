package com.mgonzalez.kafka_producer_example.application.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class KafkaProducerConsultation {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final ConcurrentHashMap<String, CompletableFuture<String>> futures = new ConcurrentHashMap<>();

    public CompletableFuture<String> send(String topic, String searchId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        futures.put(searchId, future);
        kafkaTemplate.send(topic, searchId);
        log.info("Envios desde payload : {}", searchId);
        return future;
    }

    @KafkaListener(topics = "responses_topic", groupId = "group_id")
    public void listen(String message) throws JsonProcessingException {
        // Aquí necesitarías extraer el searchId del mensaje
        String searchId = extractSearchIdFromMessage(message);
        CompletableFuture<String> future = futures.remove(searchId);
        log.info("Future - Respuesta desde consumer : {}", future);
        if (future != null) {
            log.info("Respuesta desde consumer : {}", message);
            future.complete(message);
        }
    }
    private String extractSearchIdFromMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);
        return jsonNode.get("searchId").asText();
    }
}

