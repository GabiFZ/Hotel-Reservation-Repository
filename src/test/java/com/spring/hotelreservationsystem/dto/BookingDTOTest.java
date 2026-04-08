package com.spring.hotelreservationsystem.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookingDTOTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        BookingDTO dto = new BookingDTO();

        dto.setId(1L);
        dto.setUserId(2L);
        dto.setRoomId(3L);
        dto.setCheckInDate(LocalDate.now());
        dto.setCheckOutDate(LocalDate.now().plusDays(2));
        dto.setStatus("CONFIRMED");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getUserId()).isEqualTo(2L);
        assertThat(dto.getRoomId()).isEqualTo(3L);
        assertThat(dto.getStatus()).isEqualTo("CONFIRMED");
    }
}
