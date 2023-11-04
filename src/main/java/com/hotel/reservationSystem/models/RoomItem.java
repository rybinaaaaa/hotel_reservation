package com.hotel.reservationSystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class RoomItem extends Room {

    @Column(name = "room_number")
    private int roomNumber;

    @Column
    private Boolean reserved;

    @OneToMany(mappedBy = "roomItem")
    private List<RoomCart> roomCarts;

    public RoomItem() {
    }

    public RoomItem(String name, Double price, String description, ROOM_CLASSIFICATION roomClassification, ROOM_TYPE roomType, int roomNumber, Boolean reserved) {
        super(name, price, description, roomClassification, roomType);
        this.roomNumber = roomNumber;
        this.reserved = reserved;
    }
}
