package com.spring.hotelreservationsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hotelreservationsystem.HotelReservationSystemApplication;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = HotelReservationSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCRUDOperations() throws Exception {
        //Clean DB
        roomRepository.deleteAll();

        //POST /rooms
        String roomJson = """
            {"roomType":"Single","beds":1,"price":50.0,"status":"Available"}
            """;

        mockMvc.perform(post("/api/rooms").contentType(MediaType.APPLICATION_JSON).content(roomJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.roomType").value("Single"));

        Long roomId = roomRepository.findAll().get(0).getId();

        //GET /rooms
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        //PUT /rooms/{id}
        String updatedJson = """
            {"roomType":"Double","beds":2,"price":80.0,"status":"Occupied"}
            """;

        mockMvc.perform(put("/api/rooms/" + roomId).contentType(MediaType.APPLICATION_JSON).content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beds").value(2))
                .andExpect(jsonPath("$.price").value(80.0));

        //DELETE /rooms/{id}
        mockMvc.perform(delete("/api/rooms/" + roomId))
                .andExpect(status().isNoContent());

        //Verify deletion
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}