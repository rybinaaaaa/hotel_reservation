package com.hotel.reservationSystem.services;


import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @PersistenceContext
    EntityManager em;

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
        reservation.setCreatedAt(LocalDate.now());
    }

    public Reservation findReservationByDateAndRoomNumber(LocalDate from, LocalDate to, Integer roomNumber) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);

        Join<Reservation, RoomCart> roomCartJoin = reservationRoot.join(Reservation_.roomCarts);
        Join<RoomCart, RoomItem> roomItemJoin = roomCartJoin.join(RoomCart_.roomItem);

        Predicate datePredicate = cb.and(
                cb.lessThanOrEqualTo(roomCartJoin.get(RoomCart_.reservedTo), to),
                cb.greaterThanOrEqualTo(roomCartJoin.get(RoomCart_.reservedFrom), from)
        );

        Predicate roomNumberPredicate = cb.equal(roomItemJoin.get(RoomItem_.roomNumber), roomNumber);

        query.select(reservationRoot)
                .distinct(true)
                .where(cb.and(datePredicate, roomNumberPredicate));

        List<Reservation> result = em.createQuery(query).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public List<Reservation> findReservationsByUserPhone(Integer phone) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);
        Join<Reservation, User> userJoin = reservationRoot.join(Reservation_.user);

        Predicate phonePredicate = cb.equal(userJoin.get(User_.phone), phone);

        query.select(reservationRoot)
                .where(phonePredicate);

        return em.createQuery(query).getResultList();
    }

    public List<Reservation> findReservationsByUserName(String fName, String lName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);
        Join<Reservation, User> userJoin = reservationRoot.join(Reservation_.user);

        Predicate firstNamePredicate = cb.equal(userJoin.get(User_.firstName), fName);
        Predicate lastNamePredicate = cb.equal(userJoin.get(User_.lastName), lName);

        query.select(reservationRoot)
                .where(cb.and(firstNamePredicate, lastNamePredicate));

        return em.createQuery(query).getResultList();
    }

    public List<Reservation> findReservationsByRoomNumber(Integer roomNum) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = query.from(Reservation.class);
        Join<Reservation, RoomCart> roomCartJoin = reservationRoot.join(Reservation_.roomCarts);
        Join<RoomCart, RoomItem> roomItemJoin = roomCartJoin.join(RoomCart_.roomItem);

        Predicate roomNumberPredicate = cb.equal(roomItemJoin.get(RoomItem_.roomNumber), roomNum);

        query.select(reservationRoot)
                .distinct(true)
                .where(roomNumberPredicate);

        return em.createQuery(query).getResultList();
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
