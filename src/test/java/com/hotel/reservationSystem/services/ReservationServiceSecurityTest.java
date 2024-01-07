package com.hotel.reservationSystem.services;
import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.User;
import environment.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationServiceSecurityTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired

    private UserService userService;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFindAllWithAdminRole() {
        List<Reservation> reservations = reservationService.findAll();
        Reservation reservation = new Reservation();
        reservationService.save(reservation);
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindAllWithUserRole() {
        assertThrows(AccessDeniedException.class, () -> reservationService.findAll());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFindReservationsByUserPhoneWithAdminRole() {
        String phone = "1234567890";
        User user = Generator.generateUser();
        user.setPhone(phone);
        userService.save(user);
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservationService.save(reservation);
        List<Reservation> reservations = reservationService.findReservationsByUserPhone(phone);
        assertNotNull(reservations);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFindReservationsByUserPhoneWithUserRole() {
        assertThrows(AccessDeniedException.class, () -> reservationService.findReservationsByUserPhone("1234567890"));
    }

}

