package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Room extends BaseEntity {
    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_classification")
    private RoomClassification roomClassification;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @ManyToMany
    @JoinTable(name = "category_room",
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            joinColumns = @JoinColumn(name = "room_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "room")
    private List<RoomItem> roomItems;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    public Room() {
    }

    public Room(String name, Double price, String description, RoomClassification roomClassification, RoomType roomType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.roomClassification = roomClassification;
        this.roomType = roomType;
    }

    public void addCategory(Category category) {
        if (categories == null) {
            this.categories = Collections.singletonList(category);
            return;
        }
        categories.add(category);
    }

    public void removeCategory(Category category) {
        if (categories == null) {
            return;
        }
        categories.removeIf(c -> Objects.equals(c.getId(), category.getId()));
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

    public RoomClassification getRoomClassification() {
        return roomClassification;
    }

    public void setRoomClassification(RoomClassification roomClassification) {
        this.roomClassification = roomClassification;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<RoomItem> getRoomItems() {
        return roomItems;
    }

    public void setRoomItems(List<RoomItem> roomItems) {
        this.roomItems = roomItems;
    }

    public void addRoomItem(RoomItem roomItem) {
        if (roomItem == null) return;
        if (this.roomItems == null) {
            this.roomItems = Collections.singletonList(roomItem);
            return;
        }
        if (this.roomItems.stream().map(r -> Objects.equals(r.getId(), roomItem.getId())).findAny().isEmpty()) {
            this.roomItems.add(roomItem);
        }
    }

    public void removeRoomItem(Integer id) {
        if (this.roomItems == null) return;
        this.roomItems.removeIf(r -> Objects.equals(r.getId(), id));
    }

    public List<RoomItem> getFreeRoomItems(Date from, Date to) {
        return this.roomItems.stream().filter(roomItem -> !roomItem.isReserved(from, to)).collect(Collectors.toList());
    }
}
