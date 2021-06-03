package com.example.demo.banking.dto.transformer;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.services.implementation.UtilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TransactionTransformer {

    @Autowired
    UtilityServices utilityServices;

    public Transactions depositTransactionRequestToTransaction(TransactionRequest request){
        Transactions transaction=new Transactions();
        transaction.setAmount(request.getAmount());
        transaction.setDate(new Date());
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        transaction.setCustomer(customer);
        transaction.setClosingBalance(customer.getBalance()+transaction.getAmount());

//        customer.setBalance(request.getAmount()+customer.getBalance());
        return transaction;
    }

    public Transactions withdrawTransactionRequestToTransaction(TransactionRequest request){
        Transactions transaction=new Transactions();
        transaction.setAmount(-request.getAmount());
        transaction.setDate(new Date());
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        transaction.setCustomer(customer);
        transaction.setClosingBalance(customer.getBalance()-request.getAmount());

//        customer.setBalance(request.getAmount()+customer.getBalance());
        return transaction;
    }

    public List<TransactionResponse> transactionsToTransactionResponse(List<Transactions> transactionsList){

        List<TransactionResponse> response=new ArrayList<>();
        for(Transactions transaction:transactionsList){
            TransactionResponse tr=new TransactionResponse();
            Customer customer=transaction.getCustomer();
            tr.setDate(transaction.getDate());
            tr.setAmount(transaction.getAmount());
            tr.setClosingBalance(transaction.getClosingBalance());
            response.add(tr);
        }
        return response;
    }
}
