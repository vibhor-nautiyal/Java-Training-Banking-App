package com.example.demo.banking.dto.requests;

import lombok.Data;
import lombok.Getter;

@Getter
public class ChangeDetailsRequest {

    Integer userId;

    String pin;
    String newPin;

    Long phone;

    String address;

    public ChangeDetailsRequest() {
    }

    public ChangeDetailsRequest(Integer userId, String pin, String newPin, Long phone, String address) {
        this.userId = userId;
        this.pin = pin;
        this.newPin = newPin;
        this.phone = phone;
        this.address = address;
    }
}
