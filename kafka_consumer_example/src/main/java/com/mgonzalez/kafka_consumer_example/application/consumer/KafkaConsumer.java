package com.mgonzalez.kafka_consumer_example.application.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgonzalez.kafka_consumer_example.domain.dto.FullSearchResponse;
import com.mgonzalez.kafka_consumer_example.domain.dto.SearchRequest;
import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.entity.Search;
import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaConsumer {
    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "requests", groupId = "group_id", id = "listenerId")
    public void consume(String message) throws JsonProcessingException {
        Optional.ofNullable(message)
                .map(searchRepository::findBySearchId)
                .map(search -> {
                    SearchRequest searchRequest = new SearchRequest();
                    searchRequest.setHotelId(search.getHotelId());
                    searchRequest.setCheckIn(search.getCheckIn());
                    searchRequest.setCheckOut(search.getCheckOut());
                    searchRequest.setAges(search.getAges());
                    log.info("El valor del search : {}", searchRequest);

                    FullSearchResponse fullSearchResponse = new FullSearchResponse();
                    fullSearchResponse.setSearchId(search.getSearchId());
                    fullSearchResponse.setSearch(searchRequest);
                    fullSearchResponse.setCount(search.getCount());
                    log.info("El valor del full search objeto : {}", fullSearchResponse);

                    return fullSearchResponse;
                })
                .ifPresent(fullSearchResponse ->
                {
                    try {
                        kafkaTemplate.send("responses_topic", objectMapper.writeValueAsString(fullSearchResponse));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}

