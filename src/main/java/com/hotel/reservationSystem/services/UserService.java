package com.hotel.reservationSystem.services;


import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User find(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public User findByPhone(int phone) {
        return userRepository.findByPhone(phone);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findAll(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional(readOnly = true)
    public int countByRole(ROLE role) {
        return userRepository.countByRole(role);
    }

    @Transactional(readOnly = true)
    public List<User> findUsersWithReservationsAfterSpecificDate(LocalDate date){
        return userRepository.findUsersWithReservationsAfterSpecificDate(date);
    }

    @Transactional
    public void addReservation(User user, Reservation reservation) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(reservation);
        user.addReservation(reservation);
        update(user);
    }

    @Transactional
    public void removeReservation(User user, Reservation reservation) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(reservation);
        user.removeReservation(reservation);
        update(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
