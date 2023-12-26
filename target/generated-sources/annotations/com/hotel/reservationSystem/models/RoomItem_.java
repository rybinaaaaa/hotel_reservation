package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoomItem.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class RoomItem_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.RoomItem#roomNumber
	 **/
	public static volatile SingularAttribute<RoomItem, Integer> roomNumber;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomItem#roomCarts
	 **/
	public static volatile ListAttribute<RoomItem, RoomCart> roomCarts;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomItem
	 **/
	public static volatile EntityType<RoomItem> class_;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomItem#room
	 **/
	public static volatile SingularAttribute<RoomItem, Room> room;

	public static final String ROOM_NUMBER = "roomNumber";
	public static final String ROOM_CARTS = "roomCarts";
	public static final String ROOM = "room";

}

