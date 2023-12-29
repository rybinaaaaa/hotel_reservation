package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;

import environment.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceCriteriaAPITest {

    private UserService userService;

    private CategoryService categoryService;

    private RoomItemService roomItemService;

    private RoomCartService roomCartService;

    private ReservationService reservationService;

    private RoomService roomService;

    Room room1;

    @Autowired
    public UserServiceCriteriaAPITest(RoomService roomService, UserService userService, CategoryService categoryService, RoomItemService roomItemService, RoomCartService roomCartService, ReservationService reservationService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.roomItemService = roomItemService;
        this.roomCartService = roomCartService;
        this.reservationService = reservationService;
        this.roomService = roomService;
    }


    private Room createRoom(String name, RoomType type, RoomClassification classification, Double price, List<Category> categories) {
        Room room = new Room();
        room.setName(name);
        room.setRoomType(type);
        room.setRoomClassification(classification);
        room.setPrice(price);
        room.setCategories(categories);
        roomService.save(room);
        return room;
    }

    public void setUpCart()  {
        Category category1 = new Category();
        category1.setName("Pet friendly");
        categoryService.save(category1);
        this.room1 = createRoom("Room 1", RoomType.SINGLE, RoomClassification.STANDARD, 100.0, List.of(category1));

        RoomItem roomItem1 = new RoomItem();
        roomItem1.setRoomNumber(1);
        roomItemService.addRoomItemToRoom(room1, roomItem1);
        roomItemService.save(roomItem1);

        RoomCart roomCart1 = new RoomCart(LocalDate.of(2023, 8, 10), LocalDate.of(2023, 11, 10));
        roomCartService.addRoomItemToRoomCart(roomCart1, roomItem1);
        roomCartService.save(roomCart1);

        User user = Generator.generateUser();
        user.setPhone(123);
        userService.save(user);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user);
        reservation1.setCreatedAt(LocalDate.of(2023, 11,12));
        reservation1.addRoomCart(roomCart1);
        reservationService.save(reservation1);

        roomCart1.setReservation(reservation1);
        roomCartService.update(roomCart1.getId(),roomCart1);
    }


    @Test
    public void findUserByRoomNumberAndDateTest(){
        setUpCart();
        List<User> users = userService.findUserByRoomNumberAndDate(1, LocalDate.of(2023, 8, 10),LocalDate.of(2023, 11, 10) );
        assertEquals(1, users.get(0).getId());
    }

}