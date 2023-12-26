package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(RoomCart.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class RoomCart_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.RoomCart#reservedTo
	 **/
	public static volatile SingularAttribute<RoomCart, Date> reservedTo;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomCart#reservedFrom
	 **/
	public static volatile SingularAttribute<RoomCart, Date> reservedFrom;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomCart#roomItem
	 **/
	public static volatile SingularAttribute<RoomCart, RoomItem> roomItem;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomCart#reservation
	 **/
	public static volatile SingularAttribute<RoomCart, Reservation> reservation;
	
	/**
	 * @see com.hotel.reservationSystem.models.RoomCart
	 **/
	public static volatile EntityType<RoomCart> class_;

	public static final String RESERVED_TO = "reservedTo";
	public static final String RESERVED_FROM = "reservedFrom";
	public static final String ROOM_ITEM = "roomItem";
	public static final String RESERVATION = "reservation";

}

