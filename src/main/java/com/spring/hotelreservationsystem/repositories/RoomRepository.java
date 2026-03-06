package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
