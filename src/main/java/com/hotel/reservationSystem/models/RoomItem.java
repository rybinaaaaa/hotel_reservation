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
}
