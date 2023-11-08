package com.hotel.reservationSystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Double amount;
    @Column(name = "bill_number")
    private int billNumber;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    public Payment(Double amount, int billNumber, Date date) {
        this.amount = amount;
        this.billNumber = billNumber;
        this.date = date;
    }

    public Payment() {

    }
}
