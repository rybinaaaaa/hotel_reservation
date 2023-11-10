package com.hotel.reservationSystem.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void addCategory(Category category) {
        Objects.requireNonNull(category);
        if (categories == null) {
            this.categories = new ArrayList<>();
        }
        categories.add(category);
    }

    public void removeCategory(Category category) {
        Objects.requireNonNull(category);
        if (categories == null) {
            return;
        }
        categories.removeIf(c -> Objects.equals(c.getId(), category.getId()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ROOM_CLASSIFICATION getRoomClassification() {
        return roomClassification;
    }

    public void setRoomClassification(ROOM_CLASSIFICATION roomClassification) {
        this.roomClassification = roomClassification;
    }

    public ROOM_TYPE getRoomType() {
        return roomType;
    }

    public void setRoomType(ROOM_TYPE roomType) {
        this.roomType = roomType;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
