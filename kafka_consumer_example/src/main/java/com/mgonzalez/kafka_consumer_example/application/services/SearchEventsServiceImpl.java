package com.mgonzalez.kafka_consumer_example.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgonzalez.kafka_consumer_example.domain.dto.FullSearchResponse;
import com.mgonzalez.kafka_consumer_example.domain.dto.SearchRequest;
import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.entity.Search;
import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.repository.SearchRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.mgonzalez.kafka_consumer_example.application.usecases.SearchEventsService;

import java.util.Optional;

@Service
@Slf4j
public class SearchEventsServiceImpl implements SearchEventsService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;
    @Autowired
    private SearchRepository searchRepository;

    @Override
    @Transactional
    public FullSearchResponse guardar(FullSearchResponse searchRequest) {
        log.info("Guardando StockEvent: {}", searchRequest);
        Optional.ofNullable(searchRequest.getSearch())
                .map(searchData -> {
                    Search search = new Search();
                    search.setSearchId(searchRequest.getSearchId());
                    search.setHotelId(searchData.getHotelId());
                    search.setCheckIn(searchData.getCheckIn());
                    search.setCheckOut(searchData.getCheckOut());
                    search.setAges(searchData.getAges());
                    log.info("Guardando StockEvent: {}", search);
                    searchRepository.save(search);
                    log.info("StockEvent se guardó correctamente: {}", search);
                    return search;
                })
                .ifPresent(search -> log.info("StockEvent se guardó correctamente: {}", searchRequest));
        return searchRequest;
    }
}
