package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
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
    private final PaymentService paymentService;
    private final ReservationService reservationService;

    @Autowired
    public PaymentServiceTest(PaymentService paymentService, ReservationService reservationService) {
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    @Test
    public void addPaymentToReservationSetsPaymentToReservation(){
        Payment payment = new Payment();
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);

        Payment result = paymentService.find(1);
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
        //payment was added
        assertEquals(result,result.getReservation().getPayment());
        //delete payment
        paymentService.deletePaymentFromReservation(payment);

        result = paymentService.find(1);
        assertNull(reservation.getPayment());
    }

    @Test
    public void deletePaymentFromReservationDeletesReservationFromPayment() {
        Payment payment = new Payment();
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);
        Payment result = paymentService.find(1);
        //payment was added
        assertEquals(result, result.getReservation().getPayment());
        //delete payment
        paymentService.deletePaymentFromReservation(payment);

        result = paymentService.find(1);
        assertNull(result.getReservation());
    }

}
