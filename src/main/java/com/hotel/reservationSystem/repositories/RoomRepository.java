package com.hotel.reservationSystem.repositories;

import com.hotel.reservationSystem.models.ROOM_CLASSIFICATION;
import com.hotel.reservationSystem.models.ROOM_TYPE;
import com.hotel.reservationSystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByName(String name);

    List<Room> findByPrice(Double price);

    List<Room> findByRoomType(ROOM_TYPE roomType);

    List<Room> findByRoomClassification(ROOM_CLASSIFICATION roomClassification);


}
