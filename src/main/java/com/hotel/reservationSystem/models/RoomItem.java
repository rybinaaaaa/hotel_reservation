package com.hotel.reservationSystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class RoomItem extends Room {
    @Column(name = "room_number")
    private Integer roomNumber;

    @Column
    private Boolean reserved;

    @OneToMany(mappedBy = "roomItem")
    private List<RoomCart> roomCarts;

    public RoomItem() {
    }

    public RoomItem(String name, Double price, String description, RoomClassification roomClassification, RoomType roomType, Integer roomNumber, Boolean reserved) {
        super(name, price, description, roomClassification, roomType);
        this.roomNumber = roomNumber;
        this.reserved = reserved;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public List<RoomCart> getRoomCarts() {
        return roomCarts;
    }

    public void setRoomCarts(List<RoomCart> roomCarts) {
        this.roomCarts = roomCarts;
    }
}
