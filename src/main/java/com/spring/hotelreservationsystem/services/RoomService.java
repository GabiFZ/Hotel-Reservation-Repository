package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.dto.RoomDTO;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room) {
        validateRoom(room);
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        validateRoom(updatedRoom);

        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setBeds(updatedRoom.getBeds());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setStatus(updatedRoom.getStatus());

        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new IllegalArgumentException("Room not found");
        }
        roomRepository.deleteById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<RoomDTO> getAvailableRooms() {
        return roomRepository.findByStatusIgnoreCase("AVAILABLE")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    private void validateRoom(Room room) {
        if (room.getBeds() <= 0) {
            throw new IllegalArgumentException("Beds must be > 0");
        }

        if (room.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }

        if (room.getRoomType() == null || room.getRoomType().isBlank()) {
            throw new IllegalArgumentException("Room type is required");
        }

        if (room.getStatus() == null || room.getStatus().isBlank()) {
            throw new IllegalArgumentException("Status is required");
        }
    }

    private RoomDTO mapToDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getRoomType(),
                room.getBeds(),
                room.getPrice(),
                room.getStatus()
        );
    }
}