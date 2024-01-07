package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void addPaymentToReservationSetsPaymentToReservation(){
        Payment payment = new Payment();
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);

        Payment result = paymentService.find(payment.getId());
        assertEquals(result,result.getReservation().getPayment());

    }

    @Test
    public void deletePaymentFromReservationDeletesPaymentFromReservation(){
        Payment payment = new Payment();
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);
        Payment result = paymentService.find(1);
        assertEquals(result,result.getReservation().getPayment());
        paymentService.deletePaymentFromReservation(payment);

        paymentService.find(1);
        assertNull(reservation.getPayment());
    }

    @Test
    public void deletePaymentFromReservationDeletesReservationFromPayment() {
        Payment payment = new Payment();
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);
        Payment result = paymentService.find(payment.getId());
        assertEquals(result, result.getReservation().getPayment());
        paymentService.deletePaymentFromReservation(payment);

        result = paymentService.find(payment.getId());
        assertNull(result.getReservation());
    }

}