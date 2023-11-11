package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.models.RoomClassification;
import com.hotel.reservationSystem.models.RoomType;
import com.hotel.reservationSystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room find(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> findAll(Integer page, Integer size) {
        return roomRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Room> findByName(String lastName) {
        return roomRepository.findByName(lastName);
    }

    public List<Room> findByPrice(Double price) {
        return roomRepository.findByPrice(price);
    }

    public List<Room> findByRoomType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    public List<Room> findByRoomClassification(RoomClassification roomClassification) {
        return roomRepository.findByRoomClassification(roomClassification);
    }

    @Transactional
    public void addCategory(Category category, Room room) {
//        TODO 2 sides relation
        room.addCategory(category);
    }

    @Transactional
    public void removeCategory(Category category, Room room) {
//        TODO 2 sides relation
        room.removeCategory(category);
    }

    @Transactional
    public Room update(Integer id, Room room) {
        room.setId(id);
        roomRepository.save(room);
        return room;
    }

    @Transactional
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void delete(Integer id) {
        roomRepository.deleteById(id);
    }

    private List<Room> getFreeRooms(Date from, Date to, List<Room> rooms) {
        return rooms.stream().filter(room -> !room.getFreeRoomItems(from, to).isEmpty()).collect(Collectors.toList());
    }

    public List<Room> getFilteredRoom(Integer page, Integer perPage, Date from, Date to, String category, String roomType, String roomClassification, Integer priceFrom, Integer priceTo) {
        List<Room> rooms = page != null && perPage != null ? roomRepository.findAll(PageRequest.of(page, perPage)).getContent() : roomRepository.findAll();
        if (Objects.nonNull(from) && Objects.nonNull(to)) {
            rooms = getFreeRooms(from, to, rooms);
        }
        rooms = rooms.stream().filter(r -> category == null || r.getCategories().stream().map(Category::getName).toList().contains(category)).collect(Collectors.toList());
        rooms = rooms.stream().filter(r -> roomType == null || r.getRoomType().name().equalsIgnoreCase(roomType)).collect(Collectors.toList());
        rooms = rooms.stream().filter(r -> roomType == null || r.getRoomClassification().name().equalsIgnoreCase(roomClassification)).collect(Collectors.toList());
        rooms = rooms.stream().filter(r -> priceFrom == null || r.getPrice() >= priceFrom).collect(Collectors.toList());
        rooms = rooms.stream().filter(r -> priceTo == null || r.getPrice() <= priceTo).collect(Collectors.toList());

        return rooms;
    }
}
