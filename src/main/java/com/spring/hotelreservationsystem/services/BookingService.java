package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
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

        return bookingRepository.save(booking);
    }

    // UPDATE
    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        validateBooking(updatedBooking);

        existingBooking.setFirstName(updatedBooking.getFirstName());
        existingBooking.setLastName(updatedBooking.getLastName());
        existingBooking.setEmail(updatedBooking.getEmail());
        existingBooking.setRoom(updatedBooking.getRoom());
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setPrice(updatedBooking.getPrice());
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

    // VALIDATION
    private void validateBooking(Booking booking) {

        if (booking.getFirstName() == null || booking.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (booking.getLastName() == null || booking.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }

        if (booking.getEmail() == null || booking.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("Room must be assigned");
        }

        if (booking.getCheckInDate() == null || booking.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }

        if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        if (booking.getPrice() == null || booking.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (booking.getStatus() == null || booking.getStatus().isBlank()) {
            throw new IllegalArgumentException("Booking status is required");
        }
    }
}