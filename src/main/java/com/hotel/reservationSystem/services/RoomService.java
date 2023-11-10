package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public Room find(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Room> findAll(int page, int size){
        return roomRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public List<Room> findByName(String lastName) {
        return roomRepository.findByName(lastName);
    }

    @Transactional(readOnly = true)
    public List<Room> findByPrice(Double price) {
        return roomRepository.findByPrice(price);
    }

    @Transactional(readOnly = true)
    public List<Room> findByRoomType(ROOM_TYPE roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    @Transactional(readOnly = true)
    public List<Room> findByRoomClassification(ROOM_CLASSIFICATION roomClassification) {
        return roomRepository.findByRoomClassification(roomClassification);
    }

    @Transactional
    public void addCategory(Category category, Room room) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(room);
        room.addCategory(category);
        update(room);
    }

    @Transactional
    public void removeCategory(Category category, Room room) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(room);
        room.removeCategory(category);
        update(room);
    }

    @Transactional
    public void update(Room room) {
        roomRepository.save(room);
    }

    @Transactional
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Transactional
    public void delete(int id) {
        roomRepository.deleteById(id);
    }
}
