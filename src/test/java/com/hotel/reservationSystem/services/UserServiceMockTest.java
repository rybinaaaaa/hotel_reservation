package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import java.text.SimpleDateFormat;

import com.hotel.reservationSystem.repositories.*;
import environment.Generator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.hotel.reservationSystem.models.Role.ADMIN;
import static com.hotel.reservationSystem.models.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceMockTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private CategoryService categoryService;

    private RoomService roomService;

    private RoomItemService roomItemService;

    private RoomCartService roomCartService;

    private ReservationService reservationService;

    Room room1;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public UserServiceMockTest(RoomService roomService, CategoryService categoryService, RoomItemService roomItemService, RoomCartService roomCartService, ReservationService reservationService) {
        this.roomService = roomService;
        this.categoryService = categoryService;
        this.roomItemService = roomItemService;
        this.roomCartService = roomCartService;
        this.reservationService = reservationService;
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
        User existingUser = new User("4354534454", "John", "Doe", "password", "john.doe@example.com", USER);
        existingUser.setId(userId);
        userService.save(existingUser);

        User updatedUser = new User("4336563567", "Updated", "User", "updatedPassword", "updated@example.com", ADMIN);

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
        User user = Generator.generateUser();
        userService.save(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.find(user.getId());

        assertNotNull(result);
        assertEquals(user, result);
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
        this.room1 = createRoom("Room 1", RoomType.SINGLE, RoomClassification.STANDARD, 100.0, List.of(category1));

        RoomItem roomItem1 = new RoomItem();
        roomItem1.setRoomNumber(1);
        roomItemService.addRoomItemToRoom(room1, roomItem1);
        roomItemService.save(roomItem1);

        RoomCart roomCart1 = new RoomCart(LocalDate.of(2023, 8, 10), LocalDate.of(2023, 11, 10));
        roomCartService.addRoomItemToRoomCart(roomCart1, roomItem1);
        roomCartService.save(roomCart1);

        User user = Generator.generateUser();
        user.setPhone("+380607893378");
        userService.save(user);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user);
        reservation1.setCreatedAt(LocalDate.of(2023, 11,12));
        reservation1.addRoomCart(roomCart1);
        reservationService.save(reservation1);

        roomCart1.setReservation(reservation1);
        roomCartService.update(roomCart1.getId(),roomCart1);
    }



}