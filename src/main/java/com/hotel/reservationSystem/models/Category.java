package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "category_room",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        if (rooms == null) {
            this.rooms = new ArrayList<>();
        }
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        if (rooms == null) {
            return;
        }
        rooms.removeIf(r -> Objects.equals(r.getId(), room.getId()));
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
