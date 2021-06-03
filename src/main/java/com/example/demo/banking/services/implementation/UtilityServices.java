package com.example.demo.banking.services.implementation;

import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UtilityServices {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer getCustomerById(Integer id){
        Optional<Customer> customer=customerRepo.findById(id);
        if(customer.isPresent())
            return customer.get();
        else return null;
    }
}
