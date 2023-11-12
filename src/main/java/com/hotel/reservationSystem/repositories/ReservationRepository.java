package com.hotel.reservationSystem.repositories;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByUser(User user);
}
