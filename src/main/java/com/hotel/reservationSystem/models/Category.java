package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Category extends BaseEntity {
    @Column
    private String name;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Room> rooms;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        if (room == null) return;
        if (this.rooms == null) {
            this.rooms = Collections.singletonList(room);
            return;
        }
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        if (this.rooms == null) {
            return;
        }
        this.rooms.removeIf(r -> Objects.equals(r.getId(), room.getId()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}
