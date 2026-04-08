package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Role;
import com.spring.hotelreservationsystem.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Test User");
        user.setPassword("1234");
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("test@mail.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@mail.com");
    }

    @Test
    void shouldDeleteUserByEmail() {
        User user = new User();
        user.setEmail("delete@mail.com");
        user.setName("Delete User");
        user.setPassword("1234");
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        userRepository.deleteByEmail("delete@mail.com");

        Optional<User> found = userRepository.findByEmail("delete@mail.com");

        assertThat(found).isEmpty();
    }
}