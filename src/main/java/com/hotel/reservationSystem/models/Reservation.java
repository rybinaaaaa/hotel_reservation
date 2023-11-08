package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @OneToMany(mappedBy = "reservation")
    private List<RoomCart> roomCarts;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Reservation() {
    }

    public Reservation(Date createdAt) {
        this.createdAt = createdAt;
    }
}