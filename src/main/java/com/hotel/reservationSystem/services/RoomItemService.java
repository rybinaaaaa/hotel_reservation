package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.RoomItem;
import com.hotel.reservationSystem.repositories.RoomItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomItemService {

    private final RoomItemRepository roomItemRepository;

    @Autowired
    public RoomItemService(RoomItemRepository roomItemRepository) {
        this.roomItemRepository = roomItemRepository;
    }

    @Transactional
    public void save(RoomItem roomItem) {
        roomItemRepository.save(roomItem);
    }

    @Transactional
    public void update(Integer id, RoomItem roomItem) {
        roomItem.setId(id);
        roomItemRepository.save(roomItem);
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

    public List<RoomItem> findReserved() {
        return roomItemRepository.findByReservedTrue();
    }

    public List<RoomItem> findNotReserved() {
        return roomItemRepository.findByReservedFalse();
    }
}
