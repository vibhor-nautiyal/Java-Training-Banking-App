package com.example.demo.banking.services;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.entities.Customer;

import java.security.NoSuchAlgorithmException;

public interface UtilityServices {
    Customer getCustomerById(Integer id);
    String createUser(CreateAccountRequest request);
    String md5Hasher(String pin)throws NoSuchAlgorithmException;
    String deductRDBalance();
}
