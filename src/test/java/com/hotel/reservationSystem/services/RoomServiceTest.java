package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoomServiceTest {

    private final RoomService roomService;
    private final CategoryService categoryService;
    private final RoomItemService roomItemService;
    private final RoomCartService roomCartService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private Room room1, room2, room3;

    @Autowired
    public RoomServiceTest(RoomService roomService, CategoryService categoryService, RoomItemService roomItemService, RoomCartService roomCartService) {
        this.roomService = roomService;
        this.categoryService = categoryService;
        this.roomItemService = roomItemService;
        this.roomCartService = roomCartService;
    }

    @BeforeEach
    public void setup() throws ParseException {
        Category category1 = new Category();
        category1.setName("Pet friendly");
        categoryService.save(category1);

        Category category2 = new Category();
        category2.setName("Best suggestion");
        categoryService.save(category2);

        this.room1 = createRoom("Room 1", RoomType.SINGLE, RoomClassification.STANDARD, 100.0, List.of(category1));
        this.room2 = createRoom("Room 2", RoomType.DOUBLE, RoomClassification.DELUXE, 200.0, List.of(category1, category2));
        this.room3 = createRoom("Room 3", RoomType.SINGLE, RoomClassification.DELUXE, 300.0, List.of(category2));

        roomService.save(List.of(room1, room2, room3));

        RoomItem roomItem1 = new RoomItem();
        roomItemService.addRoomItemToRoom(room1, roomItem1);

        RoomItem roomItem2 = new RoomItem();
        roomItemService.addRoomItemToRoom(room2, roomItem2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate fromReserved1 = LocalDate.parse("07-10-2023", formatter);
        LocalDate toReserved1 = LocalDate.parse("10-10-2023", formatter);
        RoomCart roomCart1 = new RoomCart(fromReserved1, toReserved1);
        roomCartService.addRoomItemToRoomCart(roomCart1, roomItem1);

        LocalDate fromReserved2 = LocalDate.parse("15-10-2023", formatter);
        LocalDate toReserved2 = LocalDate.parse("20-10-2023", formatter);
        RoomCart roomCart2 = new RoomCart(fromReserved2, toReserved2);
        roomCartService.addRoomItemToRoomCart(roomCart2, roomItem2);
    }

    @Test
    public void whenFilterByPriceRange_thenCorrectRoomsReturned() {
        List<Room> filteredRooms = roomService.getFilteredRoom(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(150.0), Optional.of(250.0));

        List<Double> prices = filteredRooms.stream().map(Room::getPrice).toList();
        assertTrue(prices.contains(200.0));
        assertEquals(1, filteredRooms.size());
    }

    @Test
    public void whenFilterByCategory_thenCorrectRoomsReturned() {
        List<Room> filteredRooms = roomService.getFilteredRoom(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable("Pet friendly"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        assertTrue(filteredRooms.stream()
                .allMatch(room -> room.getCategories().stream()
                        .anyMatch(category -> category.getName().equals("Pet friendly"))));
    }

    @Test
    public void whenFilterByRoomType_thenCorrectRoomsReturned() {
        List<Room> filteredRooms = roomService.getFilteredRoom(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("DOUBLE"), Optional.empty(), Optional.empty(), Optional.empty());

        assertTrue(filteredRooms.stream()
                .allMatch(room -> room.getRoomType().equals(RoomType.DOUBLE)));
    }

    @Test
    public void whenFilterByRoomClassification_thenCorrectRoomsReturned() {
        List<Room> filteredRooms = roomService.getFilteredRoom(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable("STANDARD"),
                Optional.empty(),
                Optional.empty()
        );

        assertTrue(filteredRooms.stream()
                .allMatch(room -> room.getRoomClassification().equals(RoomClassification.STANDARD)));
    }

    @Test
    void whenGetFilteredRoomsByDateRange_thenOnlyAvailableRoomsReturned() throws Exception {
        LocalDate from = LocalDate.parse("2023-10-06");
        LocalDate to = LocalDate.parse("2023-10-14");

        List<Room> availableRooms = roomService.getFilteredRoom(Optional.empty(), Optional.empty(), Optional.of(from), Optional.of(to), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertTrue(availableRooms.contains(roomService.find(room2.getId())));
        assertFalse(availableRooms.contains(roomService.find(room1.getId())));
    }


    private Room createRoom(String name, RoomType type, RoomClassification classification, Double price, List<Category> categories) {
        Room room = new Room();
        room.setName(name);
        room.setRoomType(type);
        room.setRoomClassification(classification);
        room.setPrice(price);
        room.setCategories(categories);
        return room;
    }
}
