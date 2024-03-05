package com.mgonzalez.kafka_consumer_example.domain.dto;

import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.entity.Search;
import lombok.Data;

@Data
public class FullSearchResponse {
    private String searchId;
    private Search search;
    private int count;
}
