package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class EnquiryRequest {
    private Integer userId;
    private String pin;

    public EnquiryRequest() {
    }

    public EnquiryRequest(Integer userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }
}
