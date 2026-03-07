package com.spring.hotelreservationsystem.controllers;

import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // GET /rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    // GET /rooms/available
    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    // POST /rooms
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room created = roomService.createRoom(room);
        return ResponseEntity.ok(created);
    }

    // PUT /rooms/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room updated = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updated);
    }

    // DELETE /rooms/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}