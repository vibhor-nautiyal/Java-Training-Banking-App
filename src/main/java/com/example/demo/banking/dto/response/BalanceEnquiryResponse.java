package com.example.demo.banking.dto.response;

import lombok.Data;

@Data
public class BalanceEnquiryResponse {

    private String name;
    private Double balance;

    public BalanceEnquiryResponse(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public BalanceEnquiryResponse() {
    }
}
