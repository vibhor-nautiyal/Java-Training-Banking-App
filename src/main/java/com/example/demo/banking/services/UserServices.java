package com.example.demo.banking.services;

import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;

import java.util.List;

public interface UserServices {
    BalanceEnquiryResponse checkBalance(EnquiryRequest request) throws InvalidCredentialsException;
    void deposit(TransactionRequest request) throws InvalidCredentialsException;
    void withdraw(TransactionRequest request) throws InsufficientBalanceException, InvalidCredentialsException;
    List<TransactionResponse> history(EnquiryRequest request) throws InvalidCredentialsException;
}