package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.Role;
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
import java.util.Optional;

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
    public void saveSavesUser() {
        User user = Generator.generateUser();
        userService.save(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(Role.USER, savedUser.getRole());
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

    @Test
    void deleteUser() {
        int userId = 1;

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findUserById() {
        int userId = 1;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User result = userService.find(userId);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

}