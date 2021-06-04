package com.example.demo.banking.controllers;

import com.example.demo.banking.Application;
import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.services.implementation.UtilityServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilityController {

    @Autowired
    UtilityServices utilityServices;

    private static final Logger log= LoggerFactory.getLogger(Application.class.getName());

    @GetMapping("/welcome")
    public String hello(){
        return "Hello SpringBoot";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody CreateAccountRequest request){
        String msg=utilityServices.createUser(request);
        log.info("Created a user");
        return msg;
    }

    @GetMapping("/getById/{id}")
    public Customer getById(@PathVariable Integer id){
        return utilityServices.getCustomerById(id);
    }
}
