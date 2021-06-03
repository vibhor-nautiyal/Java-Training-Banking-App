package com.example.demo.banking.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Transactions {

    @Id
    @Column(name="tId")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column
    private Double amount;

    @Column
    private Double balance;

    @ManyToOne
    @JoinColumn(name="cId")
    private Customer customer;

}
