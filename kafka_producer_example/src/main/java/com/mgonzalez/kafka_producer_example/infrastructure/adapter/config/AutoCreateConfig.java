package com.mgonzalez.kafka_producer_example.infrastructure.adapter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

    @Bean
    public NewTopic searchTopic(){
        return TopicBuilder.name("hotel_availability_searches")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
