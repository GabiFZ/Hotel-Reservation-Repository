package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.BookingStatus;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // CREATE
    public Booking createBooking(Booking booking) {

        validateBooking(booking);

        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    // UPDATE
    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        validateBooking(updatedBooking);

        existingBooking.setUser(updatedBooking.getUser());
        existingBooking.setRoom(updatedBooking.getRoom());
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setStatus(updatedBooking.getStatus());

        return bookingRepository.save(existingBooking);
    }

    // DELETE
    public void deleteBooking(Long id) {

        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }

        bookingRepository.deleteById(id);
    }

    // FIND ALL
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // CANCEL BOOKING
    public Booking cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }

    // VALIDATION
    private void validateBooking(Booking booking) {

        if (booking.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        }

        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("Room is required");
        }

        if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }

        if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
    }
}
