package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_classification")
    private ROOM_CLASSIFICATION roomClassification;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private ROOM_TYPE roomType;

    @ManyToMany(mappedBy = "rooms")
    private List<Category> categories;

    public Room() {
    }

    public Room(String name, Double price, String description, ROOM_CLASSIFICATION roomClassification, ROOM_TYPE roomType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.roomClassification = roomClassification;
        this.roomType = roomType;
    }
}
