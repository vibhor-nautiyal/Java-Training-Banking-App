package com.example.demo.banking.controllers;

import com.example.demo.banking.Application;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
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

    private static final Logger log= LoggerFactory.getLogger(Application.class.getName());

    @PostMapping("/deposit")
    public String deposit(@RequestBody TransactionRequest transaction) {
        try {
            userServices.deposit(transaction);
            log.info("Deposit successful");
            return "Transaction Successful";
        }
        catch (InvalidCredentialsException ex){
            log.error("Deposit failed");
            return ex.getMessage();
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody  TransactionRequest transaction){
        try{
            userServices.withdraw(transaction);
            log.info("Withdrawal successful");
            return "Transaction Successful";
        }
        catch(InvalidCredentialsException | InsufficientBalanceException ex){
            log.error("Withdrawal failed");
            return ex.getMessage();
        }
    }

    @GetMapping("/checkBalance")
    public BalanceEnquiryResponse checkBalance(@RequestBody EnquiryRequest request){
        try{
            log.info("Fetching Balanced");
            return userServices.checkBalance(request);
        }
        catch (InvalidCredentialsException ex){
//            ex.getMessage();
            log.error(ex.getMessage());
            return null;
        }
    }

    @GetMapping("/history")
    public List<TransactionResponse> history(@RequestBody EnquiryRequest request){
        try{
            log.info("Fetching History");
            return userServices.history(request);
        }
        catch(InvalidCredentialsException ex){
            log.error(ex.getMessage());
            return null;
        }
    }


    @GetMapping("getPaginatedHistory/{page}")
    public List<TransactionResponse> paginatedHistory(@RequestBody EnquiryRequest request,@PathVariable Integer page){
        try{
            log.info("Fetching page {} of history",page);
            return userServices.paginatedHstory(request,page);
        }
        catch(InvalidCredentialsException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    @PatchMapping("/changePin")
    public String changePin(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updatePin(request);
            log.info("Changed pin");
            return "Successfully changed PIN";
        }
        catch(NoSuchAlgorithmException ex){
            log.error(ex.getMessage());
            return "Couldn't change pin";
        }
        catch(InvalidCredentialsException ex){
            log.error(ex.getMessage());
            return ex.getMessage();
        }
    }
    @PatchMapping("/changePhone")
    public String changePhone(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updatePhone(request);
            log.info("Changed Phone number");
            return "Successfully changed Phone Number";
        }
        catch(InvalidCredentialsException ex){
            log.warn(ex.getMessage());
            return ex.getMessage();
        }
    }

    @PatchMapping("/changeAddress")
    public String changeAddress(@RequestBody ChangeDetailsRequest request){
        try{
            userServices.updateAddress(request);
            log.info("Changed address");
            return "Successfully changed Address";
        }
        catch(InvalidCredentialsException ex){
            log.warn(ex.getMessage());
            return ex.getMessage();
        }
    }


}
