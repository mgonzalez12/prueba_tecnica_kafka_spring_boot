package com.mgonzalez.kafka_consumer_example.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface SearchEventsService {

    public void processStockEvents(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException;
}
