package com.example.demo.banking.services.implementation;

import com.example.demo.banking.Application;
import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformerImpl;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UtilityServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityServicesImplementation implements UtilityServices {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CreateAccountTransformerImpl createAccountTransformer;

//    @Autowired
//    PasswordEncoder bcryptEncoder;

    private static final Logger log= LoggerFactory.getLogger(Application.class.getName());

    public Customer getCustomerById(Integer id){
        log.info("Getting customer with ID={}",id);
        Optional<Customer> customer=customerRepo.findById(id);
        System.out.println(customer);
        if(customer.isPresent())
            log.info("User found");
        else log.error("User not found");
        return customer.orElse(null);
    }

    public String createUser(CreateAccountRequest request){
        Customer customer=createAccountTransformer.createAccountRequestToModel(request);
//        customer.setPin(bcryptEncoder.encode(customer.getPin()));
        log.info("Creating new user");
        try{
            String hash=md5Hasher(customer.getPin());
            customer.setPin(hash);
            customerRepo.save(customer);
            log.info("Created User");
            return "User "+request.getName()+" created";
        }
        catch(NoSuchAlgorithmException ex){
            log.error("Couldn't find MD5 instance");
            return "Couldn't create user!!!";
        }
    }

    public String md5Hasher(String pin)throws NoSuchAlgorithmException{
        log.info("Hashing pin using md5");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(pin.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        System.out.println(pin+" "+hashtext);
        log.info("NoSuchAlgoException not thrown");

        return hashtext;
    }


    @Scheduled(cron = "0 0 12 1 * ?")
    public String deductRDBalance(){
        log.info("Running scheduled job to deduct RD account balance");
        List<Customer> RDCustomers=customerRepo.getByAccountType("RD");

        for(Customer customer: RDCustomers){
            if(customer.getBalance()>=100)
                customer.setBalance(customer.getBalance()-100);
            Transactions transaction=new Transactions();
            transaction.setAmount(-100.0);
            transaction.setCustomer(customer);
            transaction.setDate(new Date());
            transaction.setClosingBalance(customer.getBalance());
            customerRepo.save(customer);
            transactionRepo.save(transaction);
        }
        log.info("Deducted RD account balances");
        return "RD Balance deducted";
    }


//    @Scheduled(fixedDelay = 1000)
//    void testScheduler(){
//        System.out.println(new Date());
//    }

}
