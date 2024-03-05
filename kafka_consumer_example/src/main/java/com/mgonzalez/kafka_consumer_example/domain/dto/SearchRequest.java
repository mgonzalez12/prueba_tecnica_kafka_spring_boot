package com.mgonzalez.kafka_consumer_example.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private String hotelId;
    private String checkIn;
    private String checkOut;
    private List<Integer> ages;
}
