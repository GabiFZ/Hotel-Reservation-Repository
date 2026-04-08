package com.spring.hotelreservationsystem.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // ❌ No auth → 401
    @Test
    void roomsEndpoint_requiresAuth() throws Exception {
        mockMvc.perform(get("/api/manager/rooms"))
                .andExpect(status().isUnauthorized());
    }

    // ❌ Wrong role → 403
    @Test
    @WithMockUser(roles = "CUSTOMER")
    void roomsEndpoint_forbidden() throws Exception {
        mockMvc.perform(get("/api/manager/rooms"))
                .andExpect(status().isForbidden());   // Changed from isUnauthorized()
    }

    // ✅ Manager access → 200
    @Test
    @WithMockUser(roles = "MANAGER")
    void roomsEndpoint_allowed() throws Exception {
        mockMvc.perform(get("/api/manager/rooms"))
                .andExpect(status().isOk());
    }
}