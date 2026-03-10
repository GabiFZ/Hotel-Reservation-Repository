package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.config.name=application-test")
@ActiveProfiles("test")
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    private Booking createBooking(Room room, User user) {

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.now().plusDays(1));
        booking.setCheckOutDate(LocalDate.now().plusDays(3));
        booking.setStatus(BookingStatus.CONFIRMED);

        return booking;
    }

    private User createUser() {

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@email.com");
        user.setRole(Role.CUSTOMER);
        user.setPassword("password");

        return userRepository.save(user);
    }

    @Test
    void repositoryIsDetectedBySpring() {
        assertNotNull(bookingRepository);
    }

    @Test
    void shouldSaveBooking() {

        Room room = new Room("Single", 1, 100.0, "AVAILABLE");
        room = roomRepository.save(room);

        User user = createUser();

        Booking booking = createBooking(room, user);

        Booking savedBooking = bookingRepository.save(booking);

        assertNotNull(savedBooking.getId());
        assertEquals(user.getId(), savedBooking.getUser().getId());
    }

    @Test
    void shouldFindBookingById() {

        Room room = new Room("Double", 2, 150.0, "AVAILABLE");
        room = roomRepository.save(room);

        User user = createUser();

        Booking booking = createBooking(room, user);
        Booking savedBooking = bookingRepository.save(booking);

        Booking foundBooking =
                bookingRepository.findById(savedBooking.getId()).orElse(null);

        assertNotNull(foundBooking);
        assertEquals(savedBooking.getId(), foundBooking.getId());
    }

    @Test
    void shouldDeleteBooking() {

        Room room = new Room("Suite", 3, 300.0, "AVAILABLE");
        room = roomRepository.save(room);

        User user = createUser();

        Booking booking = createBooking(room, user);
        Booking savedBooking = bookingRepository.save(booking);

        bookingRepository.deleteById(savedBooking.getId());

        boolean exists = bookingRepository.findById(savedBooking.getId()).isPresent();

        assertFalse(exists);
    }

    @Test
    void shouldFindAllBookings() {

        Room room = new Room("Suite", 3, 300.0, "AVAILABLE");
        room = roomRepository.save(room);

        User user = createUser();

        bookingRepository.save(createBooking(room, user));
        bookingRepository.save(createBooking(room, user));

        List<Booking> bookings = bookingRepository.findAll();

        assertEquals(2, bookings.size());
        assertThat(bookings).isNotEmpty();
    }
}