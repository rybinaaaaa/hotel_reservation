package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Category_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.Category#rooms
	 **/
	public static volatile ListAttribute<Category, Room> rooms;
	
	/**
	 * @see com.hotel.reservationSystem.models.Category#name
	 **/
	public static volatile SingularAttribute<Category, String> name;
	
	/**
	 * @see com.hotel.reservationSystem.models.Category
	 **/
	public static volatile EntityType<Category> class_;

	public static final String ROOMS = "rooms";
	public static final String NAME = "name";

}

