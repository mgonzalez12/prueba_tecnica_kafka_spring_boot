package com.mgonzalez.kafka_producer_example.infrastructure.adapter.config;

import com.mgonzalez.kafka_producer_example.application.events.SearchEventProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SearchEventProducer searchEventProducer() {
        return new SearchEventProducer();
    }
}
