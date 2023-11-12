package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class RoomItem extends BaseEntity {
    @Column(name = "room_number")
    private Integer roomNumber;

    @OneToMany(mappedBy = "roomItem")
    private List<RoomCart> roomCarts;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "room_id")
    private Room room;

    public RoomItem() {
    }

    public RoomItem(Integer roomNumber, List<RoomCart> roomCarts, Room room) {
        this.roomNumber = roomNumber;
        this.roomCarts = roomCarts;
        this.room = room;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
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

    public void addRoomCart(RoomCart roomCart) {
        if (roomCart == null) return;
        if (this.roomCarts == null) {
            this.roomCarts = Collections.singletonList(roomCart);
            roomCart.setRoomItem(this);
            return;
        }
        if (this.roomCarts.stream().map(r -> Objects.equals(r.getId(), roomCart.getId())).findAny().isEmpty()) {
            this.roomCarts.add(roomCart);
            roomCart.setRoomItem(this);
        }
    }

    public void deleteRoomCartById(Integer id) {
        if (this.roomCarts == null) return;
        this.roomCarts.removeIf(r -> Objects.equals(r.getId(), id));
    }

    public Boolean isReserved(Date from, Date to) {
        if (this.roomCarts == null) return false;
        return this.roomCarts.stream().anyMatch(r ->
               !(r.getReservedTo().before(from) || r.getReservedFrom().after(to))
        );
    }
}
