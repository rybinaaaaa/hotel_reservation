package com.hotel.reservationSystem.repositories;
import com.hotel.reservationSystem.models.Payment;
import com.hotel.reservationSystem.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByBillNumber(Integer billNumber);
    Payment findByReservation(Reservation reservation);
}

