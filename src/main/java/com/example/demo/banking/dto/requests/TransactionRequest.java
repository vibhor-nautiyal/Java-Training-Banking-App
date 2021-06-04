package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class TransactionRequest {

    private Integer userId;
    private Double amount;
    private String pin;

    public TransactionRequest(Integer userId, Double amount, String pin) {
        this.userId = userId;
        this.amount = amount;
        this.pin = pin;
    }

}
