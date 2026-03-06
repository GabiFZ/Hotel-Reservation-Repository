package com.spring.hotelreservationsystem.repositories;

import com.spring.hotelreservationsystem.models.Room;
import com.spring.hotelreservationsystem.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DataJpaTest( properties = "spring.config.name=application-test")
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testRepository() {
    }
    @Test
    void repositoryIsDetectedBySpring() {

        assertNotNull(roomRepository, "RoomRepository should be detected by Spring");
    }

    @Test
    void testFindAllRooms(){

       Room room = new Room();
       room.setRoomType("Single");
       room.setBeds(1);
       room.setPrice(50.0);
       room.setStatus("Available");

        Room room2 = new Room();
        room2.setRoomType("Double");
        room2.setBeds(2);
        room2.setPrice(80.0);
        room2.setStatus("Available");

        roomRepository.save(room);
        roomRepository.save(room2);
        List<Room> rooms = roomRepository.findAll();

        assertEquals(2, rooms.size());

        rooms.forEach(r -> System.out.println(r));

        assertThat(roomRepository.findAll()).isNotEmpty();


    }
}