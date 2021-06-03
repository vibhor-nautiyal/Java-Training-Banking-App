package com.example.demo.banking.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Transactions {

    @Id
    @Column(name="tId")
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column
    Date date;

    @Column
    private Double amount;


    @Column
    private Double closingBalance;


    @ManyToOne
    @JoinColumn(name="cId")
    private Customer customer;

}
