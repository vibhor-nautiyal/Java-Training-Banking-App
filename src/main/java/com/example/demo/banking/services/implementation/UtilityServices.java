package com.example.demo.banking.services.implementation;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilityServices {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CreateAccountTransformer createAccountTransformer;

    public Customer getCustomerById(Integer id){
        Optional<Customer> customer=customerRepo.findById(id);
        return customer.orElse(null);
    }

    public void createUser(CreateAccountRequest request){
        Customer customer=createAccountTransformer.createAccountRequestToModel(request);
        customerRepo.save(customer);
    }

}
