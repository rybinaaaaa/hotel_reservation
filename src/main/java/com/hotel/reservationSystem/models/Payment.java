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

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Payment() {

    }

    public Payment(Double amount, int billNumber, Date date) {
        this.amount = amount;
        this.billNumber = billNumber;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
