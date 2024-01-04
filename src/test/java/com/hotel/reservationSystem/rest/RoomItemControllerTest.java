package com.hotel.reservationSystem.rest;

import com.hotel.reservationSystem.controllers.RoomItemController;
import com.hotel.reservationSystem.models.RoomItem;
import com.hotel.reservationSystem.services.RoomItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomItemControllerTest {

    @Mock
    private RoomItemService roomItemService;

    @InjectMocks
    private RoomItemController roomItemController;

    @Test
    public void testShowAllRoomItems() {
        when(roomItemService.findAll()).thenReturn(Arrays.asList(new RoomItem(), new RoomItem()));

        List<RoomItem> result = roomItemController.showAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testShowRoomItem() {
        when(roomItemService.find(anyInt())).thenReturn(new RoomItem());

        RoomItem result = roomItemController.show(1);

        assertEquals(RoomItem.class, result.getClass());
    }

    @Test
    public void testCreateRoomItem() {
        RoomItem roomItem = new RoomItem();

        when(roomItemService.save(any())).thenReturn(roomItem);

        ResponseEntity<RoomItem> result = roomItemController.createRoomItem(roomItem);

        verify(roomItemService, times(1)).save(roomItem);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(roomItem, result.getBody());
    }

    @Test
    public void testUpdateRoomItem() {
        RoomItem roomItem = new RoomItem();

        when(roomItemService.find(anyInt())).thenReturn(roomItem);
        when(roomItemService.update(anyInt(), any())).thenReturn(roomItem);

        ResponseEntity<RoomItem> result = roomItemController.updateRoomItem(roomItem, 1);

        verify(roomItemService, times(1)).update(1, roomItem);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(roomItem, result.getBody());
    }

    @Test
    public void testDeleteRoomItem() {
        // Performing the test
        roomItemController.deleteRoomItem(1);

        // Verifying that the roomItemService.delete() method is called once with the correct argument
        verify(roomItemService, times(1)).delete(1);
    }
}
