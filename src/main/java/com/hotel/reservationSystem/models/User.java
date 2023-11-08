package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int phone;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String password;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    private ROLE role;

    public User(int phone, String firstName, String lastName, String password, String email, ROLE role) {
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {

    }
}
