package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReservationServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        this.paymentService = new PaymentService(paymentRepository);
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
}
