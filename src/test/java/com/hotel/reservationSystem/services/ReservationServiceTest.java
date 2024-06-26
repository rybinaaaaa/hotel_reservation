package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.PaymentRepository;
import environment.Generator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    private final ReservationService reservationService;

    private final UserService userService;

    private final CategoryService categoryService;

    private final RoomService roomService;

    private final RoomItemService roomItemService;

    private final RoomCartService roomCartService;
    
    private Room room1, room2, room3;
    
    @InjectMocks
    private PaymentService paymentService;


    @Autowired
    public ReservationServiceTest( UserService userService, RoomService roomService, CategoryService categoryService, RoomItemService roomItemService, RoomCartService roomCartService, ReservationService reservationService) {
        this.roomService = roomService;
        this.categoryService = categoryService;
        this.roomItemService = roomItemService;
        this.roomCartService = roomCartService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @Test
    public void testFind() {
        Integer paymentId = 1;
        Payment payment = new Payment();
        payment.setId(paymentId);

        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.of(payment));

        Payment result = paymentService.find(paymentId);

        verify(paymentRepository, times(1)).findById(paymentId);
        assertEquals(payment, result);
    }

    @Test
    public void testFindAll() {
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> paymentList = List.of(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> result = paymentService.findAll();

        verify(paymentRepository, times(1)).findAll();
        assertEquals(paymentList, result);
    }


    @Test
    public void testFindByBillNumber() {
        Integer billNumber = 12345;
        Payment payment = new Payment();
        payment.setBillNumber(billNumber);

        when(paymentRepository.findByBillNumber(billNumber)).thenReturn(payment);

        Payment result = paymentService.findByBillNumber(billNumber);

        verify(paymentRepository, times(1)).findByBillNumber(billNumber);
        assertEquals(payment, result);
    }

    @Test
    public void testFindByReservation() {
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        Payment payment = new Payment();
        payment.setReservation(reservation);

        when(paymentRepository.findByReservation(reservation)).thenReturn(payment);

        Payment result = paymentService.findByReservation(reservation);

        verify(paymentRepository, times(1)).findByReservation(reservation);
        assertEquals(payment, result);
    }

    @Test
    public void testSave() {
        Payment payment = new Payment();

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.save(payment);

        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment, result);
    }

    @Test
    public void testDelete() {
        Integer paymentId = 1;

        paymentService.delete(paymentId);

        verify(paymentRepository, times(1)).deleteById(paymentId);
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

    public Reservation setUpCart()  {
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

        RoomCart roomCart1 = new RoomCart(LocalDate.of(2023, Calendar.AUGUST, 10), LocalDate.of(2023, Calendar.NOVEMBER, 10));
        roomCartService.addRoomItemToRoomCart(roomCart1, roomItem1);
        roomCartService.save(roomCart1);

        User user = Generator.generateUser();
        user.setPhone("380508594427");
        userService.save(user);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user);
        reservation1.setCreatedAt(LocalDate.of(2023, 11,12));
        reservation1.addRoomCart(roomCart1);
        reservationService.save(reservation1);


        roomCart1.setReservation(reservation1);
        roomCartService.update(roomCart1.getId(),roomCart1);

        return reservation1;
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    public void findReservationByDateAndRoomNumberTest(){
        Reservation expected = setUpCart();
        Reservation result = reservationService.findReservationByDateAndRoomNumber(LocalDate.of(2023, Calendar.AUGUST, 10), LocalDate.of(2023, Calendar.NOVEMBER, 10), 1 );
        assertEquals(expected.getId(), result.getId());
    }


    @Test
    public void findReservationsByUserNameTest(){
        Reservation expected = setUpCart();
        List<Reservation> result = reservationService.findReservationsByUserName("Test", "Test");
        assertEquals(expected.getId(), result.get(0).getId());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    public void findReservationsByRoomNumberTest(){
        Reservation expected = setUpCart();
        List<Reservation> result = reservationService.findReservationsByRoomNumber(1);
        assertEquals(expected.getId(), result.get(0).getId());
    }
}
