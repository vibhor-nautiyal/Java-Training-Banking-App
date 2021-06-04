package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class CreateAccountRequest {

    private String name;
    private Long phone;
    private String address;
    private String accountType;
    private String pin;

    public CreateAccountRequest(String name, Long phone, String address, String accountType, String pin) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.accountType = accountType;
        this.pin = pin;
    }

    public CreateAccountRequest() {
    }
}
