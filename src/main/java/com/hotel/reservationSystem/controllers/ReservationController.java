package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.ReservationService;
import com.hotel.reservationSystem.services.RoomCartService;
import com.hotel.reservationSystem.services.RoomService;
import com.hotel.reservationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final RoomCartService roomCartService;
    private final UserService userService;
    private final RoomService roomService;

    @Autowired
    public ReservationController(ReservationService reservationService, RoomCartService roomCartService, UserService userService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomCartService = roomCartService;
        this.userService = userService;
        this.roomService = roomService;
    }

    @PostMapping("/createWithRooms")
    public ResponseEntity<Reservation> create(@RequestParam Integer userId, @RequestParam List<RoomCart> roomCarts) {
        if (Objects.isNull(roomCarts)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.find(userId);
        Reservation reservation = reservationService.create();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        roomCartService.save(roomCarts);

        reservation.setRoomCarts(roomCarts);
        reservation.setUser(user);

        Reservation savedReservation = reservationService.save(reservation);

        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
    }

    @PutMapping("/add_room")
    public ResponseEntity<Reservation> reserveRoom(@RequestParam Integer reservationId, @RequestParam Integer roomId, @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                   @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Room room = roomService.find(roomId);
        Reservation reservation = reservationService.find(reservationId);
        if (room != null || reservation != null) {
            RoomCart roomCart = roomCartService.createRoomCart(room, from, to);
            roomCartService.setReservation(roomCart, reservation);

            return new ResponseEntity<>(reservationService.save(reservation), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity<Reservation> create(@RequestParam Integer userId) {
        if (Objects.isNull(userId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.find(userId);
        Reservation reservation = reservationService.create();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        reservation.setUser(user);
        reservationService.save(reservation);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable("id") Integer id,
            @RequestBody Reservation reservation
    ) {
        Reservation existingReservation = reservationService.find(id);
        if (existingReservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.find(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(reservationService.update(id, reservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable("id") Integer id) {
        reservationService.delete(id);
    }

    @GetMapping
    public List<Reservation> showAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public Reservation show(@PathVariable("id") Integer id) {
        return reservationService.find(id);
    }

    @GetMapping("/user/{id}")
    public List<Reservation> showByUser(@PathVariable("id") Integer id) {
        User user = userService.find(id);
        if (user == null) return null;
        return reservationService.findAllByUser(user);
    }

    @GetMapping("/dateAndRoomNumber")
    public ResponseEntity<Reservation> findReservationByDateAndRoomNumber(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam("roomNumber") Integer roomNumber) {
        Reservation reservation = reservationService.findReservationByDateAndRoomNumber(from, to, roomNumber);
        if (reservation != null) {
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/userPhone")
    public List<Reservation> findReservationsByUserPhone(@RequestParam("phone") String phone) {
        return reservationService.findReservationsByUserPhone(phone);
    }

    @GetMapping("/userName")
    public List<Reservation> findReservationsByUserName(
            @RequestParam("firstName") String fName,
            @RequestParam("lastName") String lName) {
        return reservationService.findReservationsByUserName(fName, lName);
    }

    @GetMapping("/roomNumber")
    public List<Reservation> findReservationsByRoomNumber(@RequestParam("roomNumber") Integer roomNum) {
        return reservationService.findReservationsByRoomNumber(roomNum);
    }
}
