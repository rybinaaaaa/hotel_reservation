package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    @PersistenceContext
    EntityManager em;

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

    public List<Room> getFilteredRoom(Optional<Integer> page,
                                      Optional<Integer> perPage,
                                      Optional<Date> from,
                                      Optional<Date> to,
                                      Optional<String> category,
                                      Optional<String> roomType,
                                      Optional<String> roomClassification,
                                      Optional<Integer> priceFrom,
                                      Optional<Integer> priceTo) {
//        List<Room> rooms = page != null && perPage != null ? roomRepository.findAll(PageRequest.of(page, perPage)).getContent() : roomRepository.findAll();
//        if (Objects.nonNull(from) && Objects.nonNull(to)) {
//            rooms = getFreeRooms(from, to, rooms);
//        }
//        rooms = rooms.stream().filter(r -> category == null  || r.getCategories().stream().map(Category::getName).toList().contains(category)).collect(Collectors.toList());
//        rooms = rooms.stream().filter(r -> roomType == null || r.getRoomType().name().equalsIgnoreCase(roomType)).collect(Collectors.toList());
//        rooms = rooms.stream().filter(r -> roomClassification == null || r.getRoomClassification().name().equalsIgnoreCase(roomClassification)).collect(Collectors.toList());
//        rooms = rooms.stream().filter(r -> priceFrom == null || r.getPrice() >= priceFrom).collect(Collectors.toList());
//        rooms = rooms.stream().filter(r -> priceTo == null || r.getPrice() <= priceTo).collect(Collectors.toList());
//
//        return rooms;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> room = cq.from(Room.class);

        List<Predicate> predicates = new ArrayList<>();


//      TODO

        category.ifPresent(cat -> predicates.add(cb.equal(room.get("category"), cat)));
        roomType.ifPresent(type -> predicates.add(cb.equal(room.get("roomType"), type)));
        roomClassification.ifPresent(classification -> predicates.add(cb.equal(room.get("roomClassification"), classification)));
        priceFrom.ifPresent(price -> predicates.add(cb.greaterThanOrEqualTo(room.<Integer>get("price"), price)));
        priceTo.ifPresent(price -> predicates.add(cb.lessThanOrEqualTo(room.<Integer>get("price"), price)));

        cq.where(predicates.toArray(new Predicate[0]));

        // Пагинация
        Pageable pageable = PageRequest.of(page.orElse(0), perPage.orElse(10));
        TypedQuery<Room> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Transactional
    public Room deleteRoomFromHotel(Room room) {
        Hotel hotel = room.getHotel();
        if (hotel != null) {
            room.setHotel(null);
            hotel.removeRoomById(room.getId());
        }
        return save(room);
    }

    @Transactional
    public Room addRoomToHotel(Room room, Hotel hotel) {
        room.setHotel(hotel);
        hotel.addRoom(room);
        return save(room);
    }

    @Transactional
    public List<Room> save(List<Room> rooms) {
        roomRepository.saveAll(rooms);
        return rooms;
    }
}
