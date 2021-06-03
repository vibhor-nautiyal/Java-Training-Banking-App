package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class TransactionRequest {

    private Integer userId;
    private Double amount;
}
