package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.RoomItem;
import com.hotel.reservationSystem.repositories.RoomCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomCartService {

    private final RoomCartRepository roomCartRepository;

    @Autowired
    public RoomCartService(RoomCartRepository roomCartRepository) {
        this.roomCartRepository = roomCartRepository;
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

    public void save(List<RoomCart> roomCarts) {
        roomCartRepository.saveAll(roomCarts);
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
}
