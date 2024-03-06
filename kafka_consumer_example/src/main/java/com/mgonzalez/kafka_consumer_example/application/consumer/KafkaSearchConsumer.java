package com.mgonzalez.kafka_consumer_example.application.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgonzalez.kafka_consumer_example.application.usecases.SearchEventsService;
import com.mgonzalez.kafka_consumer_example.domain.dto.FullSearchResponse;
import com.mgonzalez.kafka_consumer_example.domain.dto.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaSearchConsumer {


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    SearchEventsService searchEventsService;
    public void processStockEvents(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        FullSearchResponse stockEvent = objectMapper.readValue(consumerRecord.value(), FullSearchResponse.class);
        log.info("StockEvent recibido: {}", stockEvent);
        Optional.ofNullable(stockEvent.getSearch())
                .map(SearchRequest::getHotelId)
                .filter(hotelId -> !hotelId.isEmpty())
                .map(hotelId -> {
                    try {
                        searchEventsService.guardar(stockEvent);
                        // Luego, puedes enviar una respuesta al productor
                        kafkaTemplate.send("responses_hotel", stockEvent.getSearchId());
                    } catch (Exception ex) {
                        log.info("Tipo de evento de stock no válido");
                    }
                    return hotelId;
                })
                .orElseThrow(() -> new RecoverableDataAccessException("Problema de red temporal"));
    }

}
