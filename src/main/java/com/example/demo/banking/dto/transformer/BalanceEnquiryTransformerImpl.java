package com.example.demo.banking.dto.transformer;

import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class BalanceEnquiryTransformerImpl {

    public BalanceEnquiryResponse customerToBalanceEnquiry(Customer customer){

        BalanceEnquiryResponse response=new BalanceEnquiryResponse();
        response.setBalance(customer.getBalance());
        response.setName(customer.getName());
        return response;
    }
}
