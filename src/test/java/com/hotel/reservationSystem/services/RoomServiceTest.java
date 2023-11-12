package com.hotel.reservationSystem.services;
import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        Room expectedRoom = new Room();
        expectedRoom.setId(1);
        when(roomRepository.findById(1)).thenReturn(java.util.Optional.of(expectedRoom));

        Room actualRoom = roomService.find(1);

        assertNotNull(actualRoom);
        assertEquals(expectedRoom.getId(), actualRoom.getId());
    }

    @Test
    void testFindAll() {
        List<Room> expectedRooms = Arrays.asList(new Room(), new Room(), new Room());
        when(roomRepository.findAll()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.findAll();

        assertNotNull(actualRooms);
        assertEquals(expectedRooms.size(), actualRooms.size());
    }

    @Test
    void testFindAllPaged() {
        List<Room> expectedRooms = Arrays.asList(new Room(), new Room(), new Room());
        int page = 0;
        int perPage = 10;
        when(roomRepository.findAll(PageRequest.of(page, perPage)).getContent()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.findAll(page, perPage);

        assertNotNull(actualRooms);
        assertEquals(expectedRooms.size(), actualRooms.size());
    }

    @Test
    void testAddCategory() {
        Room room = new Room();
        Category category = new Category();

        roomService.addCategory(category, room);
        assertTrue(room.getCategories().contains(category));
    }

    @Test
    void testRemoveCategory() {
        Room room = new Room();
        Category category = new Category();
        room.addCategory(category);

        roomService.removeCategory(category, room);

        assertFalse(room.getCategories().contains(category));
    }

    @Test
    void testUpdate() {
        Room room = new Room();
        room.setId(1);

        when(roomRepository.save(room)).thenReturn(room);
        Room updatedRoom = roomService.update(1, room);

        assertNotNull(updatedRoom);
        assertEquals(room.getId(), updatedRoom.getId());
    }

    @Test
    void testSave() {
        Room room = new Room();

        when(roomRepository.save(room)).thenReturn(room);
        Room savedRoom = roomService.save(room);

        assertNotNull(savedRoom);
        assertEquals(room, savedRoom);
    }

    @Test
    void testDelete() {
        int roomId = 1;

        roomService.delete(roomId);

        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void testGetFilteredRoom() {
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        Date from = new Date();
        Date to = new Date();
        String category = "Standard";
        String roomType = "Single";
        String roomClassification = "Economy";
        int priceFrom = 50;
        int priceTo = 100;

        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> filteredRooms = roomService.getFilteredRoom(null, null, from, to, category, roomType, roomClassification, priceFrom, priceTo);

        assertNotNull(filteredRooms);
        assertEquals(3, filteredRooms.size());
    }
}

