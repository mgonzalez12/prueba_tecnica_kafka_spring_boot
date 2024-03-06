package com.mgonzalez.kafka_consumer_example.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullSearchResponse {
    private String searchId;
    private SearchRequest search;
    private int count;
}
