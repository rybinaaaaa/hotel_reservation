package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.RoomCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomCartService {

    private final RoomCartRepository roomCartRepository;
    private final RoomService roomService;

    @Autowired
    public RoomCartService(RoomCartRepository roomCartRepository, RoomService roomService) {
        this.roomCartRepository = roomCartRepository;
        this.roomService = roomService;
    }

    @Transactional
    public RoomCart save(RoomCart roomCart) {
        return roomCartRepository.save(roomCart);
    }

    @Transactional
    public void update(Integer id, RoomCart roomCart) {
        roomCart.setId(id);
        roomCartRepository.save(roomCart);
    }

    @Transactional
    public void delete(Integer id) {
        roomCartRepository.deleteById(id);
    }

    public RoomCart find(Integer id) {
        return roomCartRepository.findById(id).orElse(null);
    }

    public List<RoomCart> findAll() {
        return roomCartRepository.findAll();
    }

    public List<RoomCart> save(List<RoomCart> roomCarts) {
        return roomCartRepository.saveAll(roomCarts);
    }

    @Transactional
    public RoomCart setReservation(RoomCart roomCart, Reservation reservation) {
        roomCart.setReservation(reservation);
        reservation.addRoomCart(roomCart); // 2 side for hibernate cashing
        return save(roomCart);
    }

    @Transactional
    public RoomCart deleteReservation(RoomCart roomCart) {
        Reservation reservation = roomCart.getReservation();
        if (reservation != null) {
            roomCart.setReservation(null);
            reservation.deleteRoomCartById(reservation.getId());
        }
        return save(roomCart);
    }

    @Transactional
    public RoomCart addRoomItemToRoomCart(RoomCart roomCart, RoomItem roomItem) {
        roomCart.setRoomItem(roomItem);
        roomItem.addRoomCart(roomCart);
        return save(roomCart);
    }

    @Transactional
    public RoomCart createRoomCart(Room room, LocalDate from, LocalDate to) {
        if (to == null || from == null) throw new IllegalArgumentException();
        RoomCart roomCart = new RoomCart(from, to);
        List<RoomItem> roomItems = roomService.getRoomItemsByRoom(Optional.of(room), Optional.of(from), Optional.of(to));
        roomItems.stream().findFirst().ifPresent((roomItem) -> addRoomItemToRoomCart(roomCart, roomItem));
        return save(roomCart);
    }
}
