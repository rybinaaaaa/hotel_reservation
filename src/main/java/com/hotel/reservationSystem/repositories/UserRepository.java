package com.hotel.reservationSystem.repositories;


import com.hotel.reservationSystem.models.Role;
import com.hotel.reservationSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLastName(String name);

    User findByPhone(Integer phone);

    User findByEmail(String email);

    Integer countByRole(Role role);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.reservations r WHERE r.createdAt > :specificDate")
    List<User> findUsersWithReservationsAfterSpecificDate(@Param("specificDate") LocalDate specificDate);
}
