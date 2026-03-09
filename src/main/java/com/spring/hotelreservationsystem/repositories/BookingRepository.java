package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}