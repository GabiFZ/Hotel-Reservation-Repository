package com.spring.hotelreservationsystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hotelreservationsystem.dto.BookingRequestDTO;
import com.spring.hotelreservationsystem.models.Role;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.models.User;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import com.spring.hotelreservationsystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Long testUserId;
    private Long testRoomId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        roomRepository.deleteAll();

        // Create test user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRole(Role.CUSTOMER);
        testUserId = userRepository.save(user).getId();

        // Create test room
        Room room = new Room("Single", 1, 100.0, "AVAILABLE");
        testRoomId = roomRepository.save(room).getId();
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createBooking_shouldReturn200() throws Exception {

        BookingRequestDTO request = new BookingRequestDTO(
                testUserId,
                testRoomId,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3)
        );

        mockMvc.perform(post("/api/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").exists())
                .andExpect(jsonPath("$.message").value("Booking created successfully"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createBooking_invalidRequest_shouldReturn400() throws Exception {

        String invalidJson = "{}";

        mockMvc.perform(post("/api/bookings")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest())          // Now it will be 400
                .andExpect(content().string("User ID and Room ID are required"));
    }
}