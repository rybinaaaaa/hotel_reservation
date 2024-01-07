package com.hotel.reservationSystem.repositories;

import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.models.RoomClassification;
import com.hotel.reservationSystem.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByName(String name);

    List<Room> findByPrice(Double price);

    List<Room> findByRoomType(RoomType roomType);

    List<Room> findByRoomClassification(RoomClassification roomClassification);
}
