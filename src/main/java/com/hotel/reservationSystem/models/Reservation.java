package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@jakarta.persistence.Entity
public class Reservation extends BaseEntity {
    @Temporal(TemporalType.DATE)
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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