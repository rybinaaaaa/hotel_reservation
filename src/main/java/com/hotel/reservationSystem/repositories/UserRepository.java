package com.hotel.reservationSystem.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.reservationSystem.models.Hotel;

@Repository
public interface UserRepository extends JpaRepository<Hotel, Integer> {
}
