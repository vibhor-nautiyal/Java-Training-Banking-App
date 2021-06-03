package com.example.demo.banking.controllers;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilityController {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CreateAccountTransformer transformer;


    @GetMapping("/welcome")
    public String hello(){
        return "Hello SpringBoot";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody CreateAccountRequest request){
        Customer customer=transformer.createAccountRequestToModel(request);
        customerRepo.save(customer);
        return "User "+request.getName()+" created";
    }
}
