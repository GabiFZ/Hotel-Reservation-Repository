package com.spring.hotelreservationsystem.services;

import com.spring.hotelreservationsystem.models.*;
import com.spring.hotelreservationsystem.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingRepository bookingRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        bookingService = new BookingService(bookingRepository);
    }

    private Booking createValidBooking() {

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@email.com");

        Room room = new Room();
        room.setId(1L);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.now().plusDays(1));
        booking.setCheckOutDate(LocalDate.now().plusDays(3));
        booking.setStatus(BookingStatus.CONFIRMED);

        return booking;
    }

    @Test
    void shouldCreateBookingSuccessfully() {

        Booking booking = createValidBooking();

        when(bookingRepository.save(any())).thenReturn(booking);

        Booking saved = bookingService.createBooking(booking);

        assertNotNull(saved);
        verify(bookingRepository).save(booking);
    }

    @Test
    void shouldThrowExceptionWhenCheckOutBeforeCheckIn() {

        Booking booking = createValidBooking();
        booking.setCheckOutDate(LocalDate.now()); // invalid

        assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(booking)
        );
    }
}