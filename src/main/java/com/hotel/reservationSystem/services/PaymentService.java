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
public class PaymentService {
    PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public Payment find(int id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Payment> findAll(int page, int size){
        return paymentRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public Payment findByBillNumber(int billNumber){
        return paymentRepository.findByBillNumber(billNumber);
    }

    @Transactional(readOnly = true)
    public Payment findByReservation(Reservation reservation){
        return paymentRepository.findByReservation(reservation);
    }

    @Transactional
    public void update(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Transactional
    public void delete(int id) {
        paymentRepository.deleteById(id);
    }
}
