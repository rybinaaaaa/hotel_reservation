package com.hotel.reservationSystem.services;


import com.hotel.reservationSystem.models.Reservation;
import com.hotel.reservationSystem.models.RoomCart;
import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        enrichReservation(reservation);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation update(Integer id, Reservation reservation) {
        reservation.setId(id);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Transactional
    public void delete(Integer id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll(Sort.by(Sort.Direction.ASC, "created_at"));
    }

    public List<Reservation> findAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    public Reservation find(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public void enrichReservation(Reservation reservation) {
        reservation.setCreatedAt(new Date());
    }

    @Transactional
    public Reservation addReservationToUser(User user, Reservation reservation) {
        reservation.setUser(user);
        user.addReservation(reservation);
        return save(reservation);
    }

    @Transactional
    public Reservation deleteReservationFromUser(Reservation reservation) {
        User user = reservation.getUser();
        if (user != null) {
            reservation.setUser(null);
            user.removeReservationById(reservation.getId());
        }
        return save(reservation);
    }
}
