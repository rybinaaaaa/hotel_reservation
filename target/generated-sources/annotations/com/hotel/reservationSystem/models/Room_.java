package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Room.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Room_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.Room#price
	 **/
	public static volatile SingularAttribute<Room, Double> price;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#name
	 **/
	public static volatile SingularAttribute<Room, String> name;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#description
	 **/
	public static volatile SingularAttribute<Room, String> description;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#hotel
	 **/
	public static volatile SingularAttribute<Room, Hotel> hotel;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#roomClassification
	 **/
	public static volatile SingularAttribute<Room, RoomClassification> roomClassification;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#categories
	 **/
	public static volatile ListAttribute<Room, Category> categories;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room
	 **/
	public static volatile EntityType<Room> class_;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#roomType
	 **/
	public static volatile SingularAttribute<Room, RoomType> roomType;
	
	/**
	 * @see com.hotel.reservationSystem.models.Room#roomItems
	 **/
	public static volatile ListAttribute<Room, RoomItem> roomItems;

	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String HOTEL = "hotel";
	public static final String ROOM_CLASSIFICATION = "roomClassification";
	public static final String CATEGORIES = "categories";
	public static final String ROOM_TYPE = "roomType";
	public static final String ROOM_ITEMS = "roomItems";

}

