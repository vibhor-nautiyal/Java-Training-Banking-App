package com.example.demo.banking.controllers;

import com.example.demo.banking.dto.requests.ChangeDetailsRequest;
import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("getPaginatedHistory/{page}")
    public List<TransactionResponse> paginatedHistory(@RequestBody EnquiryRequest request,@PathVariable Integer page){
        try{
            return userServices.paginatedHstory(request,page);
        }
        catch(InvalidCredentialsException ex){
            return null;
        }
    }

    @PatchMapping("/changePin")
    public String changePin(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updatePin(request);
            return "Successfully changed PIN";
        }
        catch(InvalidCredentialsException ex){
            return ex.getMessage();
        }
    }
    @PatchMapping("/changePhone")
    public String changePhone(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updatePhone(request);
            return "Successfully changed Phone Number";
        }
        catch(InvalidCredentialsException ex){
            return ex.getMessage();
        }
    }

    @PatchMapping("/changeAddress")
    public String changeAddress(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updateAddress(request);
            return "Successfully changed Address";
        }
        catch(InvalidCredentialsException ex){
            return ex.getMessage();
        }
    }


}
