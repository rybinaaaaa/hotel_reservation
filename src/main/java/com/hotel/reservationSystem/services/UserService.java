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
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public User findByPhone(Integer phone) {
        return userRepository.findByPhone(phone);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(Integer page, Integer size){
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Integer countByRole(Role role) {
        return userRepository.countByRole(role);
    }

    public List<User> findUsersWithReservationsAfterSpecificDate(LocalDate date){
        return userRepository.findUsersWithReservationsAfterSpecificDate(date);
    }

    @Transactional
    public void addReservation(User user, Reservation reservation) {
        user.addReservation(reservation);
        reservation.setUser(user); // 2 side for hibernate cash
    }

    @Transactional
    public void removeReservation(User user, Reservation reservation) {
        user.removeReservation(reservation);
    }

    @Transactional
    public void update(Integer id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
