package com.example.demo.banking.services.implementation;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
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
public class UtilityServices {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CreateAccountTransformer createAccountTransformer;

//    @Autowired
//    PasswordEncoder bcryptEncoder;

    public Customer getCustomerById(Integer id){
        Optional<Customer> customer=customerRepo.findById(id);
        return customer.orElse(null);
    }

    public String createUser(CreateAccountRequest request){
        Customer customer=createAccountTransformer.createAccountRequestToModel(request);
//        customer.setPin(bcryptEncoder.encode(customer.getPin()));
        try{
            String hash=md5Hasher(customer.getPin());
            customer.setPin(hash);
            customerRepo.save(customer);
            return "User "+request.getName()+" created";
        }
        catch(NoSuchAlgorithmException ex){
            return "Couldn't create user!!!";
        }
    }

    public String md5Hasher(String pin)throws NoSuchAlgorithmException{

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(pin.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
    }


    @Scheduled(cron = "0 0 12 1 * ?")
    void deductRDBalance(){
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

    }


//    @Scheduled(fixedDelay = 1000)
//    void testScheduler(){
//        System.out.println(new Date());
//    }

}
