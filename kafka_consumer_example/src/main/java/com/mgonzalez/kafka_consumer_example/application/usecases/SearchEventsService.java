package com.mgonzalez.kafka_consumer_example.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgonzalez.kafka_consumer_example.domain.dto.FullSearchResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface SearchEventsService {
    public FullSearchResponse guardar(FullSearchResponse searchRequest);
}
