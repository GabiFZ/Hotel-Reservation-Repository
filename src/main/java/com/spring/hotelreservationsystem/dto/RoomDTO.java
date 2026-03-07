package com.spring.hotelreservationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String roomType;
    private int beds;
    private double price;
    private String status;
}