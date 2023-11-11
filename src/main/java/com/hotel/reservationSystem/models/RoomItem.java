package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class RoomItem extends BaseEntity {
    @Column(name = "room_number")
    private Integer roomNumber;

    @Column
    private Boolean reserved;

    @OneToMany(mappedBy = "roomItem")
    private List<RoomCart> roomCarts;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "room_id")
    private Room room;

    public RoomItem() {
    }

    public RoomItem(Integer roomNumber, Boolean reserved, List<RoomCart> roomCarts, Room room) {
        this.roomNumber = roomNumber;
        this.reserved = reserved;
        this.roomCarts = roomCarts;
        this.room = room;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean isReserved(Date from, Date to) {
        if (this.roomCarts == null) return false;
        return this.roomCarts.stream().map(r -> r.getReservedFrom().after(from) && r.getReservedTo().before(to)).findAny().isPresent();
    }
}
