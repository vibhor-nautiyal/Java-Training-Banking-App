package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class ChangeDetailsRequest {

    Integer userId;

    String pin;
    String newPin;

    Long phone;

    String address;
}
