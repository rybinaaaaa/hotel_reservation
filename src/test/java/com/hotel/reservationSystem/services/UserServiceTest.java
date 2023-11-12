package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.repositories.UserRepository;
import environment.Generator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.hotel.reservationSystem.models.Role.ADMIN;
import static com.hotel.reservationSystem.models.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void saveSavesUserAndSetsDefaultRole() {
        User user = Generator.generateUser();
        userService.save(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(USER, captor.getValue().getRole());

    }

    @Test
    public void saveSavesUserAndDoesNotSetDefaultRole() {
        User user = Generator.generateUser();
        user.setRole(ADMIN);
        userService.save(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(ADMIN, captor.getValue().getRole());

    }

    @Test
    public void addReservationAddsReservation() {
        User user = Generator.generateUser();

        Reservation reservation = new Reservation();
        reservation.setId(2);
        userService.addReservation(user, reservation);
        userService.save(user);


        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(1, savedUser.getReservations().size());
        assertEquals(reservation, savedUser.getReservations().get(0));
        assertEquals(user, reservation.getUser());
    }

    @Test
    public void removeReservationRemovesReservation() {
        User user = Generator.generateUser();
        userService.save(user);

        Reservation reservation = new Reservation();
        reservation.setId(2);

        user.addReservation(reservation);
        userService.removeReservation(user, reservation);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(0, savedUser.getReservations().size());
        assertNull(reservation.getUser());
    }

    @Test
    public void updateUpdatesUser() {
        Integer userId = 1;
        User existingUser = new User(123, "John", "Doe", "password", "john.doe@example.com", USER);
        existingUser.setId(userId);
        userService.save(existingUser);

        User updatedUser = new User(456, "Updated", "User", "updatedPassword", "updated@example.com", ADMIN);

        User result = userService.update(userId, updatedUser);
        assertEquals(userId, result.getId());
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getRole(), result.getRole());
    }

}