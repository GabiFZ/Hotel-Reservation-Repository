package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end
    ) {

        // Validate date range
        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().body("Invalid date range");
        }

        // Fetch rooms
        List<Room> availableRooms = roomRepository.findAll()
                .stream()
                .filter(room -> "AVAILABLE".equalsIgnoreCase(room.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableRooms);
    }
}