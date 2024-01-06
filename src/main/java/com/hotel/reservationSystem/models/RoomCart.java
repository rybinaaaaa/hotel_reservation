package com.hotel.reservationSystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "room_cart")
public class RoomCart extends BaseEntity {
    @Column(name = "reserved_from")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate reservedFrom;

    @Column(name = "reserved_to")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate reservedTo;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_item_id", referencedColumnName = "id")
    @JsonIgnore
    private RoomItem roomItem;

    public RoomCart() {
    }

    public RoomCart(LocalDate reservedFrom, LocalDate reservedTo) {
        this.reservedFrom = reservedFrom;
        this.reservedTo = reservedTo;
    }

    public LocalDate getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(LocalDate reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public LocalDate getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(LocalDate reservedTo) {
        this.reservedTo = reservedTo;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public RoomItem getRoomItem() {
        return roomItem;
    }

    public void setRoomItem(RoomItem roomItem) {
        this.roomItem = roomItem;
    }
}
