package com.hotel.reservationSystem.services;

import environment.Generator;
import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.models.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryService service;

    // were not run,should be checked
//    @Test
//    public void addRoomAddsRoomToTargetCategory() {
//        final Room room = Generator.generateRoom();
//        final Category category = new Category();
//        category.setName("test");
//        service.save(category);
//        service.addRoom(category, room);
//
//        final Category result = em.find(Category.class, category.getId());
//        assertTrue(result.getRooms().stream().anyMatch(r -> r.getId().equals(room.getId())));
//     }
//
//    @Test
//    public void removeRoomRemovesRoomFromTargetCategory() {
//        final Room room = Generator.generateRoom();
//        final Category category = new Category();
//        category.setName("test");
//        service.save(category);
//        service.removeRoom(category, room);
//
//        final Category result = em.find(Category.class, category.getId());
//        assertNull(result.getRooms());
//    }

}
