package com.spring.hotelreservationsystem.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomTest {

    @Test
    void constructor_shouldInitializeFields() {
        Room room = new Room("Single", 1, 50.0, "Available");

        assertThat(room.getRoomType()).isEqualTo("Single");
        assertThat(room.getBeds()).isEqualTo(1);
        assertThat(room.getPrice()).isEqualTo(50.0);
        assertThat(room.getStatus()).isEqualTo("Available");
    }

    @Test
    void setters_shouldWorkCorrectly() {
        Room room = new Room();

        room.setId(1L);
        room.setRoomType("Double");
        room.setBeds(2);
        room.setPrice(80.0);
        room.setStatus("Available");

        assertThat(room.getId()).isEqualTo(1L);
        assertThat(room.getRoomType()).isEqualTo("Double");
    }
}