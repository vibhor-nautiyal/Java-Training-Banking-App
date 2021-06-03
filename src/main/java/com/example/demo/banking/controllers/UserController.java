package com.example.demo.banking.controllers;

import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public String deposit(@RequestBody TransactionRequest transaction) {
        try {
            userServices.deposit(transaction);
            return "Transaction Successful";
        }
        catch (InvalidCredentialsException ex){
            return ex.getMessage();
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody  TransactionRequest transaction){
        try{
            userServices.withdraw(transaction);
            return "Transaction Successful";
        }
        catch(InvalidCredentialsException | InsufficientBalanceException ex){
            return ex.getMessage();
        }
    }

    @GetMapping("/checkBalance")
    public BalanceEnquiryResponse checkBalance(@RequestBody EnquiryRequest request){
        try{
            return userServices.checkBalance(request);
        }
        catch (InvalidCredentialsException ex){
//            ex.getMessage();
            return null;
        }
    }

    @GetMapping("/history")
    public List<TransactionResponse> history(@RequestBody EnquiryRequest request){
        try{
            return userServices.history(request);
        }
        catch(InvalidCredentialsException ex){
            return null;
        }
    }

    @PatchMapping("/changePin")
    public String changePin(){

    }

}
