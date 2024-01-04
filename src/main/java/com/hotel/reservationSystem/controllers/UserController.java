package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.Role;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> showAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User show(@PathVariable("id") Integer id) {
        return userService.find(id);
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        User updateUser = userService.find(id);
        if (updateUser != null) {
            return new ResponseEntity<>(userService.update(id, user), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    @GetMapping("/roomNumberAndDate")
    public List<User> findUsersByRoomNumberAndDate(
            @RequestParam("roomNumber") int roomNumber,
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return userService.findUserByRoomNumberAndDate(roomNumber, fromDate, toDate);
    }

    @GetMapping("/afterSpecificDate")
    public List<User> findUsersWithReservationsAfterSpecificDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userService.findUsersWithReservationsAfterSpecificDate(date);
    }

    @GetMapping("/countByRole")
    public Integer countUsersByRole(@RequestParam("role") Role role) {
        return userService.countByRole(role);
    }

    @GetMapping("/phone")
    public ResponseEntity<User> findUserByPhone(@RequestParam("phone") String phone) {
        User user = userService.findByPhone(phone);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name")
    public ResponseEntity<User> findUserByName(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName ) {
        User user = userService.findByName(firstName, lastName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
