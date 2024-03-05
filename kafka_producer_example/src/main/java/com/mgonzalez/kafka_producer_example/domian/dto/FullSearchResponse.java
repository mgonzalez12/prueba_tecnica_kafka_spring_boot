package com.mgonzalez.kafka_producer_example.domian.dto;

import lombok.Data;

@Data
public class FullSearchResponse {
    private String searchId;
    private SearchRequest search;
    private int count;
}
