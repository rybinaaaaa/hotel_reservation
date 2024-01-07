package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
        room.addCategory(category);
    }

    @Transactional
    public void removeCategory(Category category, Room room) {
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

    private List<Room> getFreeRooms(LocalDate from, LocalDate to, List<Room> rooms) {
        return rooms.stream().filter(room -> !room.getFreeRoomItems(from, to).isEmpty()).collect(Collectors.toList());
    }

    public List<RoomItem> getRoomItemsByRoom(Optional<Room> roomOptional, Optional<LocalDate> to, Optional<LocalDate> from) {
        Room room = roomOptional.orElseThrow(IllegalArgumentException::new);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RoomItem> cq = cb.createQuery(RoomItem.class);
        Root<Room> roomRoot = cq.from(Room.class);

        Join<Room, RoomItem> roomItemJoin = roomRoot.join(Room_.roomItems);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(roomRoot.get(Room_.id), room.getId()));

        if (to.isPresent() && from.isPresent()) {
            Join<RoomItem, RoomCart> roomCartJoin = roomItemJoin.join(RoomItem_.roomCarts, JoinType.LEFT);

            Predicate startsBeforeOrDuringTo = cb.lessThanOrEqualTo(roomCartJoin.<LocalDate>get(RoomCart_.reservedFrom), to.get());
            Predicate endsAfterOrDuringFrom = cb.greaterThanOrEqualTo(roomCartJoin.<LocalDate>get(RoomCart_.reservedTo), from.get());

            Predicate overlaps = cb.and(startsBeforeOrDuringTo, endsAfterOrDuringFrom);
            Predicate noBookingOrNotOverlapping = cb.or(cb.isNull(roomCartJoin.get(RoomCart_.id)), cb.not(overlaps));
            predicates.add(noBookingOrNotOverlapping);
        }

        cq.select(roomItemJoin).where(predicates.toArray(new Predicate[0])).distinct(true);

        TypedQuery<RoomItem> query = em.createQuery(cq);
        return query.getResultList();
    }

    public List<Room> getFilteredRoom(Optional<Integer> page,
                                      Optional<Integer> perPage,
                                      Optional<LocalDate> from,
                                      Optional<LocalDate> to,
                                      Optional<String> category,
                                      Optional<String> roomType,
                                      Optional<String> roomClassification,
                                      Optional<Double> priceFrom,
                                      Optional<Double> priceTo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> roomRoot = cq.from(Room.class);

        List<Predicate> predicates = new ArrayList<>();

        if (to.isPresent() && from.isPresent()) {
            Join<Room, RoomItem> roomItemJoin = roomRoot.join(Room_.roomItems);
            Join<RoomItem, RoomCart> roomCartJoin = roomItemJoin.join(RoomItem_.roomCarts, JoinType.LEFT);

            Predicate startsBeforeOrDuringTo = cb.lessThanOrEqualTo(roomCartJoin.<LocalDate>get(RoomCart_.reservedFrom), to.get());
            Predicate endsAfterOrDuringFrom = cb.greaterThanOrEqualTo(roomCartJoin.<LocalDate>get(RoomCart_.reservedTo), from.get());

            Predicate overlaps = cb.and(startsBeforeOrDuringTo, endsAfterOrDuringFrom);
            Predicate noBookingOrNotOverlapping = cb.or(cb.isNull(roomCartJoin.get(RoomCart_.id)), cb.not(overlaps));
//            Predicate noBookingOrNotOverlapping = cb.or(cb.isNull(roomCartJoin.get("id")), cb.not(overlaps));

            predicates.add(noBookingOrNotOverlapping);
        }

        category.ifPresent(cat -> {
            Join<Room, Category> categoryJoin = roomRoot.join(Room_.categories);
            predicates.add(cb.equal(categoryJoin.get(Category_.name), cat));
        });

        roomType.ifPresent(type -> predicates.add(cb.equal(roomRoot.get(Room_.roomType), RoomType.valueOf(type))));
        roomClassification.ifPresent(classification -> predicates.add(cb.equal(roomRoot.get(Room_.roomClassification), RoomClassification.valueOf(classification))));
        priceFrom.ifPresent(price -> predicates.add(cb.greaterThanOrEqualTo(roomRoot.get(Room_.price), price)));
        priceTo.ifPresent(price -> predicates.add(cb.lessThanOrEqualTo(roomRoot.get(Room_.price), price)));

        cq.where(predicates.toArray(new Predicate[0])).distinct(true);

        TypedQuery<Room> query = em.createQuery(cq);
        if (page.isPresent() && perPage.isPresent()) {
            query.setFirstResult(page.get() * perPage.get());
            query.setMaxResults(perPage.get());
        }

        return query.getResultList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Room deleteRoomFromHotel(Room room) {
        Hotel hotel = room.getHotel();
        if (hotel != null) {
            room.setHotel(null);
            hotel.removeRoomById(room.getId());
        }
        return save(room);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
