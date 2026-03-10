package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
        SELECT b.room FROM Booking b
        WHERE b.status = 'CONFIRMED'
        AND b.checkInDate < :end
        AND b.checkOutDate > :start
    """)
    List<Room> findBookedRooms(LocalDate start, LocalDate end);
}