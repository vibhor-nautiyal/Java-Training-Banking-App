package com.example.demo.banking.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {

    @Column(name="cId")
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column
    private Long phone;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String accountType;

    @Column
    private Double balance;

    @Column
    private String pin;

    public Customer() {
    }

    public Customer(Integer id, Long phone, String name, String address, String accountType, Double balance, String pin) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.accountType = accountType;
        this.balance = balance;
        this.pin = pin;
    }
}
