package com.spring.hotelreservationsystem.controllers;


import com.spring.hotelreservationsystem.models.Booking;
import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    //GET /bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBooking() {
//        return bookingService.getAllBookings();
        List<Booking> bookings = bookingService.getAllBookings();
        //    return new ResponseEntity<>(bookings, HttpStatus.OK);
        return ResponseEntity.ok(bookings);

    }

    // TODO: Standardize REST responses using ResponseEntity and proper HTTP status codes (200 OK, 201 CREATED, 204 NO CONTENT, ETC)


    // POST /bookings/{roomId}
    @PostMapping("/{roomId}")
    public Booking createBooking(@PathVariable Long roomId, @RequestBody Booking booking) {
        return bookingService.createBooking(roomId, booking);
    }

    // PUT /bookings/{id}
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    // DELETE /bookings/{id}
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
