package com.example.demo.banking.dto.response;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionResponse {

    private Date date;
    private Double amount;
    private Double closingBalance;
}
