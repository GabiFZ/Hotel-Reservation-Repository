package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest( properties = "spring.config.name=application-test")
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void repositoryIsDetectedBySpring() {

        assertNotNull(bookingRepository, "RoomRepository should be detected by Spring");
    }

    @Test
    void shouldSaveBooking() {

        Room room = new Room("Single", 1, 100.0, "AVAILABLE");
        room = roomRepository.save(room);

        Booking booking = new Booking();
        booking.setFirstName("John");
        booking.setLastName("Doe");
        booking.setEmail("john@example.com");
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(2));
        booking.setStatus("RESERVED");
        booking.setPrice(200.0);

        Booking savedBooking = bookingRepository.save(booking);

        assertNotNull(savedBooking.getId());
        assertEquals("John", savedBooking.getFirstName());
        assertEquals("Doe", savedBooking.getLastName());

    }

    @Test
    void shouldFindBookingById() {

        Room room = new Room("Double", 2, 150.0, "AVAILABLE");
        room = roomRepository.save(room);

        Booking booking = new Booking();
        booking.setFirstName("Ana");
        booking.setLastName("Maria");
        booking.setEmail("ana@example.com");
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(3));
        booking.setStatus("RESERVED");
        booking.setPrice(450.0);

        Booking savedBooking = bookingRepository.save(booking);

        Booking foundBooking = bookingRepository.findById(savedBooking.getId()).orElse(null);

        assertNotNull(foundBooking);
        assertEquals("Ana", foundBooking.getFirstName());
        assertEquals("Maria", foundBooking.getLastName());

    }

    @Test
    void shouldDeleteBooking() {

        Room room = new Room("Suite", 3, 300.0, "AVAILABLE");
        room = roomRepository.save(room);

        Booking booking = new Booking();
        booking.setFirstName("Mike");
        booking.setLastName("Daniel");
        booking.setEmail("mike@example.com");
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(1));
        booking.setStatus("RESERVED");
        booking.setPrice(300.0);

        Booking savedBooking = bookingRepository.save(booking);

        bookingRepository.deleteById(savedBooking.getId());

        boolean exists = bookingRepository.findById(savedBooking.getId()).isPresent();

        assertFalse(exists);

    }

    @Test
    void shouldFindAllBookings() {

        Room room = new Room("Suite", 3, 300.0, "AVAILABLE");
        room = roomRepository.save(room);

        Booking booking1 = new Booking();
        booking1.setFirstName("Mike");
        booking1.setLastName("Daniel");
        booking1.setEmail("mike@example.com");
        booking1.setRoom(room);
        booking1.setCheckInDate(LocalDate.now());
        booking1.setCheckOutDate(LocalDate.now().plusDays(1));
        booking1.setStatus("RESERVED");
        booking1.setPrice(300.0);

        Booking booking2 = new Booking();
        booking2.setFirstName("Ana");
        booking2.setLastName("Maria");
        booking2.setEmail("anna@example.com");
        booking2.setRoom(room);
        booking2.setCheckInDate(LocalDate.now());
        booking2.setCheckOutDate(LocalDate.now().plusDays(2));
        booking2.setStatus("RESERVED");
        booking2.setPrice(600.0);

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.findAll();

        assertEquals(2, bookings.size());

        bookings.forEach(b -> System.out.println(b));

        assertThat(roomRepository.findAll()).isNotEmpty();
    }
}
