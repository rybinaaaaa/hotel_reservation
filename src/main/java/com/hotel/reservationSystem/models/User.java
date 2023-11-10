package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer phone;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String password;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public User() {

    }

    public User(Integer phone, String firstName, String lastName, String password, String email, Role role) {
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void addReservation(Reservation reservation) {
        if (reservations == null) {
            this.reservations = new ArrayList<>();
        }
        if (reservations.stream().map(r -> Objects.equals(r.getId(), reservation.getId())).findAny().isPresent()) {
            reservations.add(reservation);
        }
    }

    public void removeReservation(Reservation reservation) {
        Objects.requireNonNull(reservation);
        if (reservations == null) {
            return;
        }
        reservations.removeIf(r -> Objects.equals(r.getId(), reservation.getId()));
    }

    public Integer getId() {
        return id;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
