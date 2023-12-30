package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#role
	 **/
	public static volatile SingularAttribute<User, Role> role;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#reservations
	 **/
	public static volatile ListAttribute<User, Reservation> reservations;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#phone
	 **/
	public static volatile SingularAttribute<User, String> phone;
	
	/**
	 * @see com.hotel.reservationSystem.models.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see com.hotel.reservationSystem.models.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String RESERVATIONS = "reservations";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";

}

