package com.mgonzalez.kafka_consumer_example.application.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mgonzalez.kafka_consumer_example.application.usecases.SearchEventsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class SearchEventsConsumer {
    @Autowired
    private SearchEventsService stockEventsService;

    @KafkaListener(topics = {"hotel_availability_searches"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord){
        log.info("Est es el Evento Consumer Record: {}", consumerRecord);
        try {
            stockEventsService.processStockEvents(consumerRecord);
        } catch (JsonProcessingException e) {
            log.error("Error al procesar el evento de stock", e);
        }
    }
}
