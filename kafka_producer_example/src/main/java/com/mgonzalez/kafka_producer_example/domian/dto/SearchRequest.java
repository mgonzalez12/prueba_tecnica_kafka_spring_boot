package com.mgonzalez.kafka_producer_example.domian.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private String hotelId;
    private String checkIn;
    private String checkOut;
    private List<Integer> ages;
}
