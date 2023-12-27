package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation extends BaseEntity {
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "reservation")
    private List<RoomCart> roomCarts;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    public Reservation() {
    }

    public Reservation(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoomCart> getRoomCarts() {
        return roomCarts;
    }

    public void setRoomCarts(List<RoomCart> roomCarts) {
        this.roomCarts = roomCarts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void addRoomCart(RoomCart roomCart) {
        if (roomCart == null) return;
        if (this.roomCarts == null) {
            this.roomCarts = Collections.singletonList(roomCart);
            roomCart.setReservation(this);
            return;
        }
        if (this.roomCarts.stream().map(r -> Objects.equals(r.getId(), roomCart.getId())).findAny().isEmpty()) {
            this.roomCarts.add(roomCart);
            roomCart.setReservation(this);
        }
    }

    public void deleteRoomCartById(Integer id) {
        if (this.roomCarts == null) return;
        this.roomCarts.removeIf(r -> Objects.equals(r.getId(), id));
    }
}