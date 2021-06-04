package com.example.demo.banking.dto.response;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionResponse {

    private Date date;
    private Double amount;
    private Double closingBalance;

    public TransactionResponse() {
    }

    public TransactionResponse(Date date, Double amount, Double closingBalance) {
        this.date = date;
        this.amount = amount;
        this.closingBalance = closingBalance;
    }
}
