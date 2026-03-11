package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.services.RoomService;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRoomShouldPersist() {
        Room room = new Room("Single", 1, 50.0, "Available");
        Room savedRoom = new Room( "Single", 1, 50.0, "Available");

        when(roomRepository.save(room)).thenReturn(savedRoom);

        Room saved = roomService.createRoom(room);
        //assertNotNull(saved.getId());
        assertEquals("Single", saved.getRoomType());
    }

    @Test
    void updateRoomShouldChangeValues() {
        Room existingRoom = new Room( "Single", 1, 50.0, "Available");
        Room updatedRoom = new Room( "Single", 1, 60.0, "Available");

        when(roomRepository.findById(existingRoom.getId())).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);

        Room updated = roomService.updateRoom(existingRoom.getId(), updatedRoom);

        assertEquals(60.0, updated.getPrice());
    }

    @Test
    void deleteRoomShouldRemove() {
        Long roomId = 1L;

        when(roomRepository.existsById(roomId)).thenReturn(true);
        doNothing().when(roomRepository).deleteById(roomId);

        roomService.deleteRoom(roomId);

        verify(roomRepository, times(1)).deleteById(roomId);
    }
}