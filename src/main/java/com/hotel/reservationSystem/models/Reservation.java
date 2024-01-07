package com.hotel.reservationSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation extends BaseEntity {
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "reservation")
    private List<RoomCart> roomCarts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    public Reservation() {
        this.createdAt = LocalDate.now();
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

    public void addRoomCart(RoomCart ...roomCartsToAdd) {
        if (roomCartsToAdd == null) return;

        if (this.roomCarts == null) {
            this.roomCarts = new ArrayList<>(Arrays.asList(roomCartsToAdd));
        } else {
            for (RoomCart newRoomCart : roomCartsToAdd) {
                if (this.roomCarts.stream().noneMatch(r -> Objects.equals(r.getId(), newRoomCart.getId()))) {
                    this.roomCarts.add(newRoomCart);
                }
            }
        }

        for (RoomCart roomCart : roomCartsToAdd) {
            roomCart.setReservation(this);
        }
    }


    public void deleteRoomCartById(Integer id) {
        if (this.roomCarts == null) return;
        this.roomCarts.removeIf(r -> Objects.equals(r.getId(), id));
    }
}