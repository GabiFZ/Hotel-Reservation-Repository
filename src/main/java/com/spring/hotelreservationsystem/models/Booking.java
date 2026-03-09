package com.spring.hotelreservationsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "first_name" , length = 65, nullable = false)
    private String firstName;

    @Column(name = "guest_last_name" , length = 65, nullable = false)
    private String lastName;

    @Column(name = "guest_email" , length = 65, nullable = false, unique = true)
    private String email;

    @Column(name = "check_in_date" , nullable = false)
    private LocalDate checkInDate;

    @Column(name = "chec_out_date" , nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "price" , nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    private String status;
}