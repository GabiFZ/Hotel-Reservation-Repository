package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatusIgnoreCase(String status);
}