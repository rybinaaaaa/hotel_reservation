package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.RoomItem;
import com.hotel.reservationSystem.repositories.RoomItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoomItemService {

    private final RoomItemRepository roomItemRepository;

    @Autowired
    public RoomItemService(RoomItemRepository roomItemRepository) {
        this.roomItemRepository = roomItemRepository;
    }

    @Transactional
        public RoomItem save(RoomItem roomItem) {
        return roomItemRepository.save(roomItem);
    }

    @Transactional
    public RoomItem update(Integer id, RoomItem roomItem) {
        roomItem.setId(id);
        return roomItemRepository.save(roomItem);
    }

    @Transactional
    public void delete(Integer id) {
        roomItemRepository.deleteById(id);
    }

    public RoomItem find(Integer id) {
        return roomItemRepository.findById(id).orElse(null);
    }

    public List<RoomItem> findAll() {
        return roomItemRepository.findAll();
    }

    @Transactional
    public RoomItem addRoomItemToRoom(Room room, RoomItem roomItem) {
        roomItem.setRoom(room);
        room.addRoomItem(roomItem);
        return save(roomItem);
    }

    @Transactional
    public RoomItem deleteRoomItemFromRoom(Room room, RoomItem roomItem) {
        roomItem.setRoom(null);
        room.removeRoomItem(roomItem.getId());
        return save(roomItem);
    }
}
