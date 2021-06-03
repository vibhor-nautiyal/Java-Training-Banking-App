package com.example.demo.banking.services.implementation;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.transformer.BalanceEnquiryTransformer;
import com.example.demo.banking.dto.transformer.TransactionTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserServiceImplementation implements UserServices{


    @Autowired
    UtilityServices utilityServices;

    @Autowired
    private TransactionTransformer transactionTransformer;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private BalanceEnquiryTransformer balanceEnquiryTransformer;

    public BalanceEnquiryResponse checkBalance(Integer id){

        Customer customer=utilityServices.getCustomerById(id);
        BalanceEnquiryResponse response=balanceEnquiryTransformer.customerToBalanceEnquiry(customer);
        return response;
    }

    public void deposit(TransactionRequest request){
        Transactions transactions=transactionTransformer.depositTransactionRequestToTransaction(request);
        Customer customer=transactions.getCustomer();
        customer.setBalance(customer.getBalance()+request.getAmount());
        customerRepo.save(customer);
        transactionRepo.save(transactions);
    }

    public void withdraw(TransactionRequest request) throws InsufficientBalanceException {

        Transactions transactions=transactionTransformer.withdrawTransactionRequestToTransaction(request);
        Customer customer=transactions.getCustomer();
        if(customer.getBalance()<request.getAmount())
            throw new InsufficientBalanceException("Insufficient Balance");
        customer.setBalance(customer.getBalance() + request.getAmount());
        customerRepo.save(customer);
        transactionRepo.save(transactions);

    }
    public List<Transactions> history(){

//        return user.getStatement();
        return new ArrayList<>();
    }

}
