package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Reservation.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Reservation_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.Reservation#createdAt
	 **/
	public static volatile SingularAttribute<Reservation, LocalDate> createdAt;
	
	/**
	 * @see com.hotel.reservationSystem.models.Reservation#roomCarts
	 **/
	public static volatile ListAttribute<Reservation, RoomCart> roomCarts;
	
	/**
	 * @see com.hotel.reservationSystem.models.Reservation#payment
	 **/
	public static volatile SingularAttribute<Reservation, Payment> payment;
	
	/**
	 * @see com.hotel.reservationSystem.models.Reservation
	 **/
	public static volatile EntityType<Reservation> class_;
	
	/**
	 * @see com.hotel.reservationSystem.models.Reservation#user
	 **/
	public static volatile SingularAttribute<Reservation, User> user;

	public static final String CREATED_AT = "createdAt";
	public static final String ROOM_CARTS = "roomCarts";
	public static final String PAYMENT = "payment";
	public static final String USER = "user";

}

