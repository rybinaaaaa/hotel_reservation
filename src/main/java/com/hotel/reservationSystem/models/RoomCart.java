package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "room_cart")
public class RoomCart extends SuperMappedByClass {
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

    public Date getReservedFrom() {
        return reservedFrom;
    }

    public void setReservedFrom(Date reservedFrom) {
        this.reservedFrom = reservedFrom;
    }

    public Date getReservedTo() {
        return reservedTo;
    }

    public void setReservedTo(Date reservedTo) {
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
