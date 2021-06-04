package com.example.demo.banking.services.implementation;

import com.example.demo.banking.dto.requests.ChangeDetailsRequest;
import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.dto.transformer.BalanceEnquiryTransformer;
import com.example.demo.banking.dto.transformer.TransactionTransformer;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserServices{


    @Autowired
    UtilityServices utilityServices;

    @Autowired
    private TransactionTransformer transactionTransformer;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private BalanceEnquiryTransformer balanceEnquiryTransformer;

//    @Autowired
//    PasswordEncoder bcryptEncoder;

    public boolean authenticate(Integer id,String pin){
        Customer customer=utilityServices.getCustomerById(id);
//        return bcryptEncoder.matches(pin,customer.getPin());
        try {
            String hash = md5Hasher(pin);
            return customer.getPin().equals(hash);
        }
        catch(NoSuchAlgorithmException ex){

            return false;
        }
    }

    public String md5Hasher(String pin)throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(pin.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }


    public BalanceEnquiryResponse checkBalance(EnquiryRequest request) throws  InvalidCredentialsException{
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        BalanceEnquiryResponse response=balanceEnquiryTransformer.customerToBalanceEnquiry(customer);
        return response;
    }

    public void deposit(TransactionRequest request) throws InvalidCredentialsException {

        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Transactions transactions=transactionTransformer.depositTransactionRequestToTransaction(request);
        Customer customer=transactions.getCustomer();
        customer.setBalance(customer.getBalance()+request.getAmount());
        customerRepo.save(customer);
        transactionRepo.save(transactions);
    }

    public void withdraw(TransactionRequest request) throws InsufficientBalanceException,InvalidCredentialsException {
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Transactions transactions=transactionTransformer.withdrawTransactionRequestToTransaction(request);
        Customer customer=transactions.getCustomer();
        if(customer.getBalance()<request.getAmount())
            throw new InsufficientBalanceException("Insufficient Balance");
        customer.setBalance(customer.getBalance() - request.getAmount());
        customerRepo.save(customer);
        transactionRepo.save(transactions);

    }
    public List<TransactionResponse> history(EnquiryRequest request) throws  InvalidCredentialsException{

        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        List<Transactions> transactions=transactionRepo.findByCId(request.getUserId());
        List<TransactionResponse> response=transactionTransformer.transactionsToTransactionResponse(transactions);
        return response;
    }

    public void updatePin(ChangeDetailsRequest request)throws InvalidCredentialsException{
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        customer.setPin(request.getNewPin());
        customerRepo.save(customer);
    }

    public void updatePhone(ChangeDetailsRequest request) throws InvalidCredentialsException{
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        customer.setPhone(request.getPhone());
        customerRepo.save(customer);
    }
    public void updateAddress(ChangeDetailsRequest request) throws InvalidCredentialsException{
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");
        Customer customer=utilityServices.getCustomerById(request.getUserId());
        customer.setAddress(request.getAddress());
        customerRepo.save(customer);
    }

    @Override
    public List<TransactionResponse> paginatedHstory(EnquiryRequest request,Integer page) throws InvalidCredentialsException {
        if(!authenticate(request.getUserId(),request.getPin()))
            throw new InvalidCredentialsException("Invalid Credentials");

        Integer recordPagePage=5;

        Pageable pageable= PageRequest.of(page,recordPagePage);

        Page<Transactions> transactions=transactionRepo.findByCidPaged(request.getUserId(),pageable);

        List<Transactions> pageToList = new ArrayList<>();
        if(transactions != null && transactions.hasContent()) {
            pageToList = transactions.getContent();
        }

        List<TransactionResponse> response=transactionTransformer.transactionsToTransactionResponse(pageToList);
        return response;
    }

}
