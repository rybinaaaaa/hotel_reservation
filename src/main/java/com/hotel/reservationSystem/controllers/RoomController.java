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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
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
            Optional<Integer> page,
            Optional<Integer> perPage,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> to,
            Optional<String> category,
            Optional<String> roomType,
            Optional<String> roomClassification,
            Optional<Double> priceFrom,
            Optional<Double> priceTo) {

        return roomService.getFilteredRoom(
                page,
                perPage,
                from,
                to,
                category,
                roomType,
                roomClassification,
                priceFrom,
                priceTo);
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
    @PutMapping ("/{id}")
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
