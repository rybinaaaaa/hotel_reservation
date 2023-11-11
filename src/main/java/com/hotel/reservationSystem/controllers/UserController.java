package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> showAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.find(id);
    }

    @PostMapping()
    ResponseEntity<User> create(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public  ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id){
        User updateUser = userService.find(id);
        if(updateUser != null){
            updateUser.//TO DO
        }
        else {
            user.setId(id);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("{id}")
    void deleteEmployee(@PathVariable Integer id) {
        userService.delete(id);
    }
}
