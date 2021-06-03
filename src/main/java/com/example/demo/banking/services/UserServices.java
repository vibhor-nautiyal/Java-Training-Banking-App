package com.example.demo.banking.services;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;

import java.util.List;

public interface UserServices {
    BalanceEnquiryResponse checkBalance(Integer id);
    void deposit(TransactionRequest request);
    void withdraw(TransactionRequest request) throws InsufficientBalanceException;
    List<Transactions> history();
}