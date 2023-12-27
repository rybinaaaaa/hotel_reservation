package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import java.text.SimpleDateFormat;

import com.hotel.reservationSystem.repositories.*;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

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

    private CategoryService categoryService;

    private RoomService roomService;

    private RoomItemService roomItemService;

    private RoomCartService roomCartService;

    private ReservationService reservationService;

    Room room1, room2, room3;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public UserServiceTest(UserService userService, RoomService roomService, CategoryService categoryService, RoomItemService roomItemService, RoomCartService roomCartService, ReservationService reservationService) {
        this.roomService = roomService;
        this.categoryService = categoryService;
        this.roomItemService = roomItemService;
        this.roomCartService = roomCartService;
        this.reservationService = reservationService;
        this.userService = userService;
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
        User existingUser = new User("123", "John", "Doe", "password", "john.doe@example.com", USER);
        existingUser.setId(userId);
        userService.save(existingUser);

        User updatedUser = new User("456", "Updated", "User", "updatedPassword", "updated@example.com", ADMIN);

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

    private Room createRoom(String name, RoomType type, RoomClassification classification, Double price, List<Category> categories) {
        Room room = new Room();
        room.setName(name);
        room.setRoomType(type);
        room.setRoomClassification(classification);
        room.setPrice(price);
        room.setCategories(categories);
        return room;
    }

    public void setUpCart()  {
        Category category1 = new Category();
        category1.setName("Pet friendly");
        categoryService.save(category1);

        Category category2 = new Category();
        category2.setName("Best suggestion");
        categoryService.save(category2);

        this.room1 = createRoom("Room 1", RoomType.SINGLE, RoomClassification.STANDARD, 100.0, List.of(category1));
        this.room2 = createRoom("Room 2", RoomType.DOUBLE, RoomClassification.DELUXE, 200.0, List.of(category1, category2));
        this.room3 = createRoom("Room 3", RoomType.SINGLE, RoomClassification.DELUXE, 300.0, List.of(category2));
        roomService.save(List.of(room1, room2, room3));

        RoomItem roomItem1 = new RoomItem();
        roomItem1.setRoomNumber(1);
        roomItemService.addRoomItemToRoom(room1, roomItem1);
        roomItemService.save(roomItem1);

        RoomCart roomCart1 = new RoomCart(new Date(2023, Calendar.AUGUST, 10), new Date(2023, Calendar.NOVEMBER, 10));
        roomCartService.addRoomItemToRoomCart(roomCart1, roomItem1);
        roomCartService.save(roomCart1);

        User user = Generator.generateUser();
        userService.save(user);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCreatedAt(new Date(2023, 12,12));
        reservation.addRoomCart(roomCart1);
        reservationService.save(reservation);

        roomCart1.setReservation(reservation);
        roomCartService.update(roomCart1.getId(),roomCart1);
    }


    @Test
    public void findUserByRoomNumberAndDate(){
        setUpCart();
        List<User> users = userService.findUserByRoomNumberAndDate(1, new Date(2023, Calendar.AUGUST, 10),new Date(2023, Calendar.NOVEMBER, 10) );
        assertEquals(1, users.get(0).getId());
    }

}