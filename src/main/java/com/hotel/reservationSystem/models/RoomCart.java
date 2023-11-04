package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "room_cart")
public class RoomCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reserved_from")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date reservedFrom;

    @Column(name = "reserved_to")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date reservedTo;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_item_id", referencedColumnName = "id")
    private RoomItem roomItem;

    public RoomCart() {
    }

    public RoomCart(Date reservedFrom, Date reservedTo) {
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
    }
}
