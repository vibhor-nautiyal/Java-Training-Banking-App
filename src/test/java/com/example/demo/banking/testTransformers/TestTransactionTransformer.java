package com.example.demo.banking.testTransformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.dto.transformer.TransactionTransformerImpl;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.services.UtilityServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestTransactionTransformer {

    @InjectMocks
    TransactionTransformerImpl transformer;

    @Mock
    UtilityServices utilityServices;

    @Test
    public void test_depositTransactionRequestToTransaction(){

        Customer customer=new Customer(1,9999L,"ABC","XYZ","FD",10.0,"1234");
        Transactions transactions=new Transactions(1,new Date(),10.0,10.0,customer);
        TransactionRequest request=new TransactionRequest(1,10.0,"1234");

        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);

        assertEquals(transactions.getDate().toString(),transformer.depositTransactionRequestToTransaction(request).getDate().toString());
        assertEquals(null,transformer.depositTransactionRequestToTransaction(request).getId());

    }

    @Test
    public void test_withdrawTransactionRequestToTransaction(){

        Customer customer=new Customer(1,9999L,"ABC","XYZ","FD",10.0,"1234");
        Transactions transactions=new Transactions(1,new Date(),-10.0,0.0,customer);
        TransactionRequest request=new TransactionRequest(1,10.0,"1234");

        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);

        assertEquals(transactions.getDate().toString(),transformer.withdrawTransactionRequestToTransaction(request).getDate().toString());

    }

    @Test
    public void test_transactionsToTransactionResponse(){

        Customer customer=new Customer(1,9999L,"ABC","XYZ","FD",10.0,"1234");
        Transactions transactions=new Transactions(1,new Date(),-10.0,10.0,customer);
        List<Transactions> input=new ArrayList<>();
        input.add(transactions);

        TransactionResponse response=new TransactionResponse(new Date(),10.0,10.0);

        assertEquals(response.getDate().toString(),transformer.transactionsToTransactionResponse(input).get(0).getDate().toString());
        assertEquals(response.getClosingBalance(),transformer.transactionsToTransactionResponse(input).get(0).getClosingBalance());
    }


}
