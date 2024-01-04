package com.hotel.reservationSystem.rest;

import com.hotel.reservationSystem.controllers.RoomController;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    public void testShowAllRooms() {
        when(roomService.findAll()).thenReturn(Arrays.asList(new Room(), new Room()));

        List<Room> result = roomController.showAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetFilteredRooms() {
        when(roomService.getFilteredRoom(any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Arrays.asList(new Room(), new Room()));

        List<Room> result = roomController.getFilteredRooms(
                Optional.of(1),
                Optional.of(10),
                Optional.of(LocalDate.now()),
                Optional.of(LocalDate.now().plusDays(7)),
                Optional.of("TestCategory"),
                Optional.of("Double"),
                Optional.of("Luxury"),
                Optional.of(100.0),
                Optional.of(200.0));

        assertEquals(2, result.size());
    }

    @Test
    public void testShowRoom() {
        when(roomService.find(anyInt())).thenReturn(new Room());

        Room result = roomController.show(1);

        assertEquals(Room.class, result.getClass());
    }

    @Test
    public void testAddRoom() {
        Room room = new Room();

        when(roomService.save(room)).thenReturn(room);

        ResponseEntity<Room> result = roomController.addRoom(room);

        verify(roomService, times(1)).save(room);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(room, result.getBody());
    }

    @Test
    public void testUpdateRoom() {
        Room room = new Room();

        when(roomService.update(anyInt(), any())).thenReturn(room);

        ResponseEntity<Room> result = roomController.updateRoom(room, 1);

        verify(roomService, times(1)).update(1, room);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(room, result.getBody());
    }

}
