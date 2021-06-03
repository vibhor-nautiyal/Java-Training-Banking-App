package com.example.demo.banking.controllers;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @PostMapping("/deposit")
    public String deposit(TransactionRequest transaction){
        userServices.deposit(transaction);
        return "Transaction Successful";
    }

    @PostMapping("/withdraw")
    public String withdraw(TransactionRequest transaction){
        try{
            userServices.withdraw(transaction);
            return "Transaction Successful";
        }
        catch (InsufficientBalanceException ex){
            return ex.getMessage();
        }
    }

    @GetMapping("/checkBalance/{id}")
    public BalanceEnquiryResponse checkBalance(@PathVariable Integer id){
        return userServices.checkBalance(id);
    }

}
