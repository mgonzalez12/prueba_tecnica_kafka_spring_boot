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
    public void processStockEvents(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        FullSearchResponse stockEvent = objectMapper.readValue(consumerRecord.value(), FullSearchResponse.class);
        log.info("StockEvent recibido: {}", stockEvent);

        if (stockEvent.getSearch().getHotelId() != null  && stockEvent.getSearch().getHotelId().isEmpty()) {
            throw new RecoverableDataAccessException("Problema de red temporal");
        }

        try{
            save(stockEvent);
        }catch (Exception ex){
            log.info("Tipo de evento de stock no válido");
        }

    }

    @Transactional
    private void save(FullSearchResponse searchRequest) {
        log.info("Guardando StockEvent: {}", searchRequest);
        Search search = new Search();
        search.setSearchId(searchRequest.getSearchId());
        search.setHotelId(searchRequest.getSearch().getHotelId());
        search.setCheckIn(searchRequest.getSearch().getCheckIn());
        search.setCheckOut(searchRequest.getSearch().getCheckOut());
        search.setAges(searchRequest.getSearch().getAges());

        log.info("Guardando StockEvent: {}", search);
        searchRepository.save(search);
        log.info("StockEvent se guardó correctamente: {}", search);
        log.info("StockEvent se guardó correctamente: {}", searchRequest);
    }
}
