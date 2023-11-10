package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment find(Integer id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> findAll(Integer page, Integer size) {
        return paymentRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Payment findByBillNumber(Integer billNumber) {
        return paymentRepository.findByBillNumber(billNumber);
    }

    public Payment findByReservation(Reservation reservation) {
        return paymentRepository.findByReservation(reservation);
    }

    @Transactional
    public void update(Integer id, Payment payment) {
        payment.setId(id);
        paymentRepository.save(payment);
    }

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public void delete(Integer id) {
        paymentRepository.deleteById(id);
    }
}
