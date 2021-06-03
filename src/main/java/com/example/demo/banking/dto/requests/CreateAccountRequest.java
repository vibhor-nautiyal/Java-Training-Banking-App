package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String name;
    private Long phone;
    private String address;
    private String accountType;
    private String pin;
}
