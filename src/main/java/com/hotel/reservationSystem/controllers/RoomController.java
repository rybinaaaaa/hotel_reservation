package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> showAll() {
        return roomService.findAll();
    }

    @GetMapping("/filtered")
    public List<Room> getFilteredRooms(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "roomType", required = false) String roomType,
            @RequestParam(name = "roomClassification", required = false) String roomClassification,
            @RequestParam(name = "priceFrom", required = false) Integer priceFrom,
            @RequestParam(name = "priceTo", required = false) Integer priceTo) {

        return roomService.getFilteredRoom(page, perPage, from, to, category, roomType, roomClassification, priceFrom, priceTo);
    }

    @GetMapping("/{id}")
    public Room show(@PathVariable("id") Integer id) {
        return roomService.find(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }
    @PatchMapping ("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room, @PathVariable("id") Integer id) {
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(roomService.update(id, room), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable("id") Integer id) {
        roomService.delete(id);
    }
}
