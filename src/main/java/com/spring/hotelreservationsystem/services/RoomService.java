package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room) {
        if (room.getBeds() <= 0)
            throw new IllegalArgumentException("Beds must be > 0");
        if (room.getPrice() < 0)
            throw new IllegalArgumentException("Price must be >= 0");
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setBeds(updatedRoom.getBeds());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setStatus(updatedRoom.getStatus());
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id))
            throw new IllegalArgumentException("Room not found");
        roomRepository.deleteById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }
}