package com.example.demo.banking.testTransformers;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.transformer.BalanceEnquiryTransformerImpl;
import com.example.demo.banking.dto.transformer.CreateAccountTransformerImpl;
import com.example.demo.banking.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestBalanceTransformer {

    @Autowired
    BalanceEnquiryTransformerImpl transformer;

    @Test
    public void test_customerToBalanceEnquiry(){
        Customer customer=new Customer(1,9999L,"ABC","XYZ","FD",0.0,"1234");
        BalanceEnquiryResponse response=new BalanceEnquiryResponse("ABC",0.0);
        assertEquals(customer.getBalance(),transformer.customerToBalanceEnquiry(customer).getBalance());
    }


}
