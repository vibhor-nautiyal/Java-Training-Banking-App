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


}
