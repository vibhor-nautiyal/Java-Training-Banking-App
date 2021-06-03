package com.example.demo.banking.dto.transformer;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.services.implementation.UtilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionTransformer {

    @Autowired
    UtilityServices utilityServices;

    public Transactions depositTransactionRequestToTransaction(TransactionRequest request){
        Transactions transaction=new Transactions();
        transaction.setAmount(request.getAmount());
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        transaction.setBalance(request.getAmount()+customer.getBalance());
//        customer.setBalance(request.getAmount()+customer.getBalance());
        return transaction;
    }

    public Transactions withdrawTransactionRequestToTransaction(TransactionRequest request){
        Transactions transaction=new Transactions();
        transaction.setAmount(-request.getAmount());
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        transaction.setBalance(request.getAmount()+customer.getBalance());
//        customer.setBalance(request.getAmount()+customer.getBalance());
        return transaction;
    }
}
