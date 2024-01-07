package com.hotel.reservationSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Category extends BaseEntity {
    @Column
    private String name;

    @JsonIgnore
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
