package com.example.demo.banking.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Transactions {



    @Id
    @Column(name="tId")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column
    private Date date;

    @Column
    private Double amount;


    @Column
    private Double closingBalance;


    @ManyToOne
    @JoinColumn(name="cId")
    private Customer customer;

    public Transactions() {
    }

    public Transactions(Integer id, Date date, Double amount, Double closingBalance, Customer customer) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.closingBalance = closingBalance;
        this.customer = customer;
    }
}
