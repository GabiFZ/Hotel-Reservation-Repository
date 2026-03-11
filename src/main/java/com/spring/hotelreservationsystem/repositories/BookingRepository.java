package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT b.room FROM Booking b
        WHERE b.checkInDate < :end
          AND b.checkOutDate > :start
    """)
    List<Room> findBookedRooms(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}