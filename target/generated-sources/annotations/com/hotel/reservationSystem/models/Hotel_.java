package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Hotel.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Hotel_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.Hotel#rooms
	 **/
	public static volatile ListAttribute<Hotel, Room> rooms;
	
	/**
	 * @see com.hotel.reservationSystem.models.Hotel#address
	 **/
	public static volatile SingularAttribute<Hotel, String> address;
	
	/**
	 * @see com.hotel.reservationSystem.models.Hotel#name
	 **/
	public static volatile SingularAttribute<Hotel, String> name;
	
	/**
	 * @see com.hotel.reservationSystem.models.Hotel
	 **/
	public static volatile EntityType<Hotel> class_;

	public static final String ROOMS = "rooms";
	public static final String ADDRESS = "address";
	public static final String NAME = "name";

}

