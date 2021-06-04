package com.example.demo.banking.testServices;

import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.transformer.BalanceEnquiryTransformerImpl;
import com.example.demo.banking.dto.transformer.TransactionTransformerImpl;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import com.example.demo.banking.services.UtilityServices;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
//@RequiredArgsConstructor
public class TestUserServices {

    @Mock
    private UserServices userServices;

    @Mock
    private TransactionTransformerImpl transactionTransformer;

    @Mock
    private UtilityServices utilityServices;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private BalanceEnquiryTransformerImpl balanceEnquiryTransformer;

    @Test
    public void test_deposit(){

//        TransactionRequest transactionRequest= Mockito.mock(TransactionRequest.class);
//        Customer customer=Mockito.mock(Customer.class);
//        Transactions transaction=Mockito.mock(Transactions.class);
//        Mockito.when(userServices.authenticate(Mockito.anyInt(),Mockito.anyString())).thenReturn(true);
//        Mockito.when(transactionTransformer.depositTransactionRequestToTransaction(transactionRequest)).thenReturn(Mockito.mock(Transactions.class));
//        Mockito.when(transaction.getCustomer()).thenReturn(customer);
//
//        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
//        Mockito.when(transactionRepo.save(transaction)).thenReturn(transaction);
//        String msg;
//        try{
//            msg=userServices.deposit(transactionRequest);
//        }
//        catch(InvalidCredentialsException ex){
//            msg=ex.getMessage();
//        }
//        assertEquals("Transaction Successful",msg);
    }
}
