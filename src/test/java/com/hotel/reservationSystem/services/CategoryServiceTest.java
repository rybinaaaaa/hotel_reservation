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
    private CategoryService service;
    private RoomService roomService;

    @Autowired
    public CategoryServiceTest(CategoryService service, RoomService roomService) {
        this.service = service;
        this.roomService = roomService;
    }


    @Test
    public void addRoomToCategoryAddsRoomToTargetCategory() {
        final Room room = Generator.generateRoom();
        final Category category = new Category();
        category.setName("test");
        category.setId(1);
        service.save(category);
        roomService.save(room);
        room.setId(2);
        service.addRoomToCatagory(room, category);

        final Category result = service.find(category.getId());
        assertTrue(result.getRooms().stream().anyMatch(r -> r.getId().equals(room.getId())));
     }

    @Test
    public void addRoomToCategoryAddsCategoryToTargetRoom() {
        final Room room = Generator.generateRoom();
        final Category category = new Category();
        category.setName("test");
        category.setId(1);
        service.save(category);
        roomService.save(room);
        room.setId(2);
        service.addRoomToCatagory(room, category);

        final Category result = service.find(category.getId());
        assertEquals(room.getId(), result.getRooms().get(0).getId());
    }

    @Test
    public void deleteRoomFromCategoryDeletesRoomFromTargetCategory() {
        final Room room = Generator.generateRoom();
        room.setId(2);
        roomService.save(room);


        final Category category = new Category();
        category.setName("test");
        category.setId(1);
        service.save(category);
        service.deleteRoomFromCategory(room, category);

        final Category result = service.find(category.getId());
        assertNull(result.getRooms());
    }

    @Test
    public void deleteRoomFromCategoryDeletesCategoryFromTargetRoom() {
        final Room room = Generator.generateRoom();
        room.setId(2);
        roomService.save(room);

        final Category category = new Category();
        category.setName("test");
        category.setId(1);
        service.save(category);
        service.deleteRoomFromCategory(room, category);

        final Room result = roomService.find(room.getId());
        assertNull(result.getCategories());
    }


}
