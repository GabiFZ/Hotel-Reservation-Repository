package com.spring.hotelreservationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hotelreservationsystem.HotelReservationSystemApplication;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HotelReservationSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test") // optional, use application-test.properties for H2
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Clear DB
        roomRepository.deleteAll();

        // Add sample rooms
        Room room1 = new Room("Single", 1, 50.0, "AVAILABLE");
        Room room2 = new Room("Double", 2, 80.0, "AVAILABLE");
        Room room3 = new Room("Suite", 3, 150.0, "OCCUPIED");

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
    }

    @Test
    void shouldReturnAvailableRoomsForValidDates() throws Exception {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(3);

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // only AVAILABLE rooms
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].roomType", anyOf(is("Single"), is("Double"))))
                .andExpect(jsonPath("$[0].beds", anyOf(is(1), is(2))))
                .andExpect(jsonPath("$[0].price", anyOf(is(50.0), is(80.0))))
                .andExpect(jsonPath("$[0].status", is("AVAILABLE")));
    }

    @Test
    void shouldReturnEmptyListIfNoRoomsAvailable() throws Exception {
        // Mark all rooms as OCCUPIED
        roomRepository.findAll().forEach(r -> {
            r.setStatus("OCCUPIED");
            roomRepository.save(r);
        });

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(2);

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnBadRequestForInvalidDateRange() throws Exception {
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1); // end before start

        mockMvc.perform(get("/api/availability")
                        .param("start", start.toString())
                        .param("end", end.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}