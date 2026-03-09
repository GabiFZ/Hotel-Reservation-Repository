package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public Booking createBooking(Long roomId, Booking booking){

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Invalid booking dates");
        }

        booking.setRoom(room);

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id){

        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found");
        }

        bookingRepository.deleteById(id);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Room room = roomRepository.findById(updatedBooking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        existingBooking.setFirstName(updatedBooking.getFirstName());
        existingBooking.setLastName(updatedBooking.getLastName());
        existingBooking.setEmail(updatedBooking.getEmail());
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setStatus(updatedBooking.getStatus());
        existingBooking.setPrice(updatedBooking.getPrice());
        existingBooking.setRoom(room);

        return bookingRepository.save(existingBooking);
    }
}
