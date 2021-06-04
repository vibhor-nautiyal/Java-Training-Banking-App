package com.example.demo.banking.dto.transformer;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountTransformerImpl {

    public Customer createAccountRequestToModel(CreateAccountRequest request){

        Customer customer=new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setAccountType(request.getAccountType());
        customer.setPin(request.getPin());
        customer.setBalance(0.0);
        return customer;
    }
}
