package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.RoomCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoomCartServiceTest {
    private final RoomCartService roomCartService;
    private ReservationService reservationService;

    @Autowired
    public RoomCartServiceTest(RoomCartService roomCartService, ReservationService reservationService) {
        this.roomCartService = roomCartService;
        this.reservationService = reservationService;
    }

    @Test
    public void setReservationSetsReservationToTargetRoomCart(){
        RoomCart roomCart = new RoomCart();
        roomCart.setId(1);
        roomCartService.save(roomCart);

        Reservation reservation = new Reservation();
        reservation.setId(2);
        Reservation saved = reservationService.save(reservation);

        roomCartService.setReservation(roomCart, reservation);
        RoomCart result = roomCartService.find(roomCart.getId());
        assertEquals(saved, result.getReservation());
    }

    @Test
    public void setReservationSetsRoomCartToTargetReservation(){
        RoomCart roomCart = new RoomCart();
        RoomCart savedRoomCart = roomCartService.save(roomCart);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        roomCartService.setReservation(roomCart, reservation);
        Reservation result = reservationService.find(reservation.getId());
        assertEquals(savedRoomCart, result.getRoomCarts().get(0));
    }

    @Test
    public void deleteReservationDeletesReservationFromTargetRoomCart(){
        RoomCart roomCart = new RoomCart();
        roomCart.setId(1);
        RoomCart savedRoomCart = roomCartService.save(roomCart);

        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservationService.save(reservation);

        roomCartService.setReservation(roomCart, reservation);
        RoomCart result = roomCartService.find(roomCart.getId());
        roomCartService.deleteReservation(savedRoomCart);
        assertNull(result.getReservation());
    }

    @Test
    public void deleteReservationDeletesRoomCartFromTargetReservation(){
        RoomCart roomCart = new RoomCart();
        roomCart.setId(1);
        RoomCart savedRoomCart = roomCartService.save(roomCart);

        Reservation reservation = new Reservation();
        reservation.setId(2);
        Reservation savedReservation = reservationService.save(reservation);

        roomCartService.setReservation(roomCart, reservation);
        RoomCart result = roomCartService.find(roomCart.getId());
        assertEquals(savedReservation, result.getReservation());

        roomCartService.deleteReservation(savedRoomCart);
        assertNull(savedReservation.getRoomCarts());
    }
}
