package com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.repository;

import com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {

    int countBySearchId(String searchId);
   // Optional<Search> findBySearchId(String searchId);
   Search findBySearchId(String searchId);

}
