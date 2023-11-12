package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.RoomItem;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.RoomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//to check in the morning
@RestController
@RequestMapping("/room_items")
public class RoomItemController {
    private final RoomItemService service;

    @Autowired
    public RoomItemController(RoomItemService service) {
        this.service = service;
    }

    @GetMapping()
    public List<RoomItem> showAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RoomItem show(@PathVariable("id") Integer id) {
        return service.find(id);
    }

    @PostMapping()
    public ResponseEntity<RoomItem> createRoomItem(@RequestBody RoomItem roomItem) {
        if (roomItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.save(roomItem), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomItem> updateRoomItem(@RequestBody RoomItem roomItem, @PathVariable("id") Integer id) {
        RoomItem roomItemUpdate = service.find(id);
        if (roomItemUpdate != null) {
            return new ResponseEntity<>(service.update(id, roomItem), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomItem(@PathVariable("id") Integer id) {
        service.delete(id);
    }

}
