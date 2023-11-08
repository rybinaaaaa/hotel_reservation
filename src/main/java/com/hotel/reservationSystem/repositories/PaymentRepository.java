package com.hotel.reservationSystem.repositories;
import com.hotel.reservationSystem.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Hotel, Integer> {
}

