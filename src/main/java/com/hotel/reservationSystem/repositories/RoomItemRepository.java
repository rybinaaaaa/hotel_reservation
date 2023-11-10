package com.hotel.reservationSystem.repositories;

import com.hotel.reservationSystem.models.RoomItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomItemRepository  extends JpaRepository<RoomItem, Integer> {
    List<RoomItem> findByReservedTrue();

    List<RoomItem> findByReservedFalse();
}
