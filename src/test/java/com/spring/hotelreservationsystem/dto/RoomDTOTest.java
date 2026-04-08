package com.spring.hotelreservationsystem.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomDTOTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        RoomDTO dto = new RoomDTO();

        dto.setId(1L);
        dto.setRoomType("Double");
        dto.setBeds(2);
        dto.setPrice(100.0);
        dto.setStatus("Available");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getRoomType()).isEqualTo("Double");
        assertThat(dto.getBeds()).isEqualTo(2);
        assertThat(dto.getPrice()).isEqualTo(100.0);
        assertThat(dto.getStatus()).isEqualTo("Available");
    }
}