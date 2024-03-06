package com.mgonzalez.kafka_producer_example.domian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class FullSearchResponse {
    private String searchId;
    private SearchRequest search;
    private int count;

}
