package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
