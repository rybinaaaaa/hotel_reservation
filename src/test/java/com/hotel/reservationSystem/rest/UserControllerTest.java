package com.hotel.reservationSystem.rest;

import com.hotel.reservationSystem.controllers.UserController;
import com.hotel.reservationSystem.models.Role;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testShowAll() {
        when(userService.findAll()).thenReturn(Arrays.asList(new User(), new User()));

        List<User> result = userController.showAll();

        assertEquals(2, result.size());
    }


    @Test
    public void testCreateUser() {
        when(userService.save(any())).thenReturn(new User());

        ResponseEntity<User> result = userController.createUser(new User());

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(User.class, result.getBody().getClass());
    }

    @Test
    public void testUpdateUser() {
        when(userService.find(anyInt())).thenReturn(new User());
        when(userService.update(anyInt(), any())).thenReturn(new User());

        ResponseEntity<User> result = userController.updateUser(new User(), 1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(User.class, result.getBody().getClass());
    }


    @Test
    public void testFindUsersByRoomNumberAndDate() {
        when(userService.findUserByRoomNumberAndDate(anyInt(), any(), any())).thenReturn(Arrays.asList(new User()));

        List<User> result = userController.findUsersByRoomNumberAndDate(101, LocalDate.now(), LocalDate.now());

        assertEquals(1, result.size());
    }

    @Test
    public void testFindUsersWithReservationsAfterSpecificDate() {
        when(userService.findUsersWithReservationsAfterSpecificDate(any())).thenReturn(Arrays.asList(new User()));

        List<User> result = userController.findUsersWithReservationsAfterSpecificDate(LocalDate.now());

        assertEquals(1, result.size());
    }

    @Test
    public void testCountUsersByRole() {
        when(userService.countByRole(any())).thenReturn(5);

        Integer result = userController.countUsersByRole(Role.USER);

        assertEquals(5, result.intValue());
    }

    @Test
    public void testFindUserByPhone() {
        when(userService.findByPhone(anyString())).thenReturn(new User());

        ResponseEntity<User> result = userController.findUserByPhone("1234567890");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(User.class, result.getBody().getClass());
    }

    @Test
    public void testFindUserByName() {
        when(userService.findByName(anyString(), anyString())).thenReturn(new User());

        ResponseEntity<User> result = userController.findUserByName("John", "Doe");

        // Verifying the result
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(User.class, result.getBody().getClass());
    }
}
