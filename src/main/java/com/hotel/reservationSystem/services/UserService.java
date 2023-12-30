package com.hotel.reservationSystem.services;


import com.hotel.reservationSystem.exception.UserAlreadyExistsException;
import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional()
public class UserService{
    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

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

        public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        Predicate emailPredicate = cb.equal(userRoot.get(User_.email), email);

        query.where(emailPredicate).distinct(true);

        return em.createQuery(query).getResultList();
    }

    public List<User> findUserByRoomNumberAndDate(int roomNumber, LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        Join<User, Reservation> reservationsJoin = userRoot.join(User_.reservations);
        Join<Reservation, RoomCart> roomCartsJoin = reservationsJoin.join(Reservation_.roomCarts);
        Join<RoomCart, RoomItem> roomItemsJoin = roomCartsJoin.join(RoomCart_.roomItem);

        Predicate roomNumberPredicate = cb.equal(roomItemsJoin.get(RoomItem_.roomNumber), roomNumber);

        Predicate dateRangePredicate = cb.between(
                roomCartsJoin.get(RoomCart_.reservedFrom).as(LocalDate.class),
                fromDate,
                toDate
        );

        Predicate finalPredicate = cb.and(roomNumberPredicate, dateRangePredicate);
        query.select(userRoot).distinct(true).where(finalPredicate);

        return em.createQuery(query).getResultList();
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

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(Role.USER);

        return userRepository.save(user);    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    @Transactional
    public User update(Integer id, User user) {
        user.setId(id);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User save(User user) {
        if(user.getRole() == null)
            user.setDefaultRole();
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
