package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaymentServiceTest {
    private PaymentService paymentService;
    private ReservationService reservationService;

    @Autowired
    public PaymentServiceTest(PaymentService paymentService, ReservationService reservationService) {
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    @Test
    public void addPaymentToReservationSetsPaymentToReservation(){
        Payment payment = new Payment();
        payment.setId(1);
        paymentService.save(payment);

        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservationService.save(reservation);

        paymentService.addPaymentToReservation(payment, reservation);

        Payment result = paymentService.find(1);
        assertEquals(result,result.getReservation().getPayment() );

    }
}
