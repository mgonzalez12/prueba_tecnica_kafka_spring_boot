package com.mgonzalez.kafka_consumer_example.insfrastruture.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    @Id
    private String searchId;

    @Column(name = "hotelId")
    private String hotelId;
    @Column(name = "checkIn")
    private String checkIn;
    @Column(name = "checkOut")
    private String checkOut;
    private List<Integer> ages;
    private int count;
}
