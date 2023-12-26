package com.hotel.reservationSystem.models;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Payment.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Payment_ extends com.hotel.reservationSystem.models.BaseEntity_ {

	
	/**
	 * @see com.hotel.reservationSystem.models.Payment#date
	 **/
	public static volatile SingularAttribute<Payment, Date> date;
	
	/**
	 * @see com.hotel.reservationSystem.models.Payment#amount
	 **/
	public static volatile SingularAttribute<Payment, Double> amount;
	
	/**
	 * @see com.hotel.reservationSystem.models.Payment#reservation
	 **/
	public static volatile SingularAttribute<Payment, Reservation> reservation;
	
	/**
	 * @see com.hotel.reservationSystem.models.Payment#billNumber
	 **/
	public static volatile SingularAttribute<Payment, Integer> billNumber;
	
	/**
	 * @see com.hotel.reservationSystem.models.Payment
	 **/
	public static volatile EntityType<Payment> class_;

	public static final String DATE = "date";
	public static final String AMOUNT = "amount";
	public static final String RESERVATION = "reservation";
	public static final String BILL_NUMBER = "billNumber";

}

