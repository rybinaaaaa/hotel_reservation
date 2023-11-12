package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Hotel extends BaseEntity {
    @Column
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    public Hotel() {
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        if (room == null) return;
        if (this.rooms == null) {
            this.rooms = Collections.singletonList(room);
            return;
        }
        if (rooms.stream().map(r -> Objects.equals(r.getId(), room.getId())).findAny().isEmpty()) {
            this.rooms.add(room);
            room.setHotel(this);
        }
    }

    public void removeRoomById(Integer id) {
        if (this.rooms == null) return;
        this.rooms.removeIf(r -> Objects.equals(r.getId(), id));
    }
}
