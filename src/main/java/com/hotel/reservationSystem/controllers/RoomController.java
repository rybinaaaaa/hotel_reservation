package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Room> showAll(){
        return roomService.findAll();
    }

//    @PostMapping("/{id}/add")
//    public ResponseEntity<HttpStatus> addToCart(@RequestBody Integer userId, @PathVariable("id") Integer numberId) {
//        roomService.
//    }
}
