package com.hotel.reservationSystem.rest;

import com.hotel.reservationSystem.controllers.ReservationController;
import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.services.ReservationService;
import com.hotel.reservationSystem.services.RoomCartService;
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
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private RoomCartService roomCartService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void testCreateReservation() {
        when(userService.find(anyInt())).thenReturn(new User());

//        when(roomCartService.save(anyList())).thenReturn(Arrays.asList(new RoomCart()));

        when(reservationService.create()).thenReturn(new Reservation(LocalDate.now()));

        ResponseEntity<Reservation> result = reservationController.create(1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(Reservation.class, result.getBody().getClass());
    }

    @Test
    public void testUpdateReservation() {
        when(reservationService.find(anyInt())).thenReturn(new Reservation());

        when(userService.find(anyInt())).thenReturn(new User());

        when(reservationService.update(anyInt(), any())).thenReturn(new Reservation());

        ResponseEntity<Reservation> result = reservationController.update(1, new Reservation());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(Reservation.class, result.getBody().getClass());
    }

    @Test
    public void testShowAllReservations() {
        when(reservationService.findAll()).thenReturn(Arrays.asList(new Reservation(), new Reservation()));

        List<Reservation> result = reservationController.showAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testShowReservation() {
        when(reservationService.find(anyInt())).thenReturn(new Reservation());

        Reservation result = reservationController.show(1);

        assertEquals(Reservation.class, result.getClass());
    }

    @Test
    public void testShowReservationsByUser() {
        when(userService.find(anyInt())).thenReturn(new User());

        when(reservationService.findAllByUser(any())).thenReturn(Arrays.asList(new Reservation(), new Reservation()));

        List<Reservation> result = reservationController.showByUser(1);

        assertEquals(2, result.size());
    }

    @Test
    public void testFindReservationByDateAndRoomNumber() {
        when(reservationService.findReservationByDateAndRoomNumber(any(), any(), anyInt())).thenReturn(new Reservation());

        ResponseEntity<Reservation> result = reservationController.findReservationByDateAndRoomNumber(
                LocalDate.now(), LocalDate.now(), 101);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(Reservation.class, result.getBody().getClass());
    }

    @Test
    public void testFindReservationsByUserPhone() {
        when(reservationService.findReservationsByUserPhone(anyString())).thenReturn(Arrays.asList(new Reservation(), new Reservation()));

        List<Reservation> result = reservationController.findReservationsByUserPhone("1234567890");

        assertEquals(2, result.size());
    }

    @Test
    public void testFindReservationsByUserName() {
        when(reservationService.findReservationsByUserName(anyString(), anyString())).thenReturn(Arrays.asList(new Reservation(), new Reservation()));

        List<Reservation> result = reservationController.findReservationsByUserName("John", "Doe");

        assertEquals(2, result.size());
    }

    @Test
    public void testFindReservationsByRoomNumber() {
        when(reservationService.findReservationsByRoomNumber(anyInt())).thenReturn(Arrays.asList(new Reservation(), new Reservation()));

        List<Reservation> result = reservationController.findReservationsByRoomNumber(101);

        assertEquals(2, result.size());
    }
}

