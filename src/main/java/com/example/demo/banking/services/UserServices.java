package com.example.demo.banking.services;

import com.example.demo.banking.dto.requests.ChangeDetailsRequest;
import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import org.springframework.data.domain.Page;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserServices {
    BalanceEnquiryResponse checkBalance(EnquiryRequest request) throws InvalidCredentialsException;
    String deposit(TransactionRequest request) throws InvalidCredentialsException;
    String withdraw(TransactionRequest request) throws InsufficientBalanceException, InvalidCredentialsException;
    List<TransactionResponse> history(EnquiryRequest request) throws InvalidCredentialsException;

    String updatePin(ChangeDetailsRequest request) throws InvalidCredentialsException, NoSuchAlgorithmException;

    String updatePhone(ChangeDetailsRequest request) throws InvalidCredentialsException;

    String updateAddress(ChangeDetailsRequest request) throws InvalidCredentialsException;
    boolean authenticate(Integer id,String pin);
    List<TransactionResponse> paginatedHistory(EnquiryRequest request,Integer page) throws InvalidCredentialsException;
}