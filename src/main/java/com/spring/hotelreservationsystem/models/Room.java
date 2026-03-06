package com.spring.hotelreservationsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Room")
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "beds" , nullable = false)
    private int beds;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "status", nullable = false)
    private String status;




}
