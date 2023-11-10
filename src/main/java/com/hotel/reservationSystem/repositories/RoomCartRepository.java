package com.hotel.reservationSystem.repositories;

import com.hotel.reservationSystem.models.RoomCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomCartRepository extends JpaRepository<RoomCart, Integer> {
}
