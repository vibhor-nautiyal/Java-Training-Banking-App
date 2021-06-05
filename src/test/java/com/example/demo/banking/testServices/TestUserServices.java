package com.example.demo.banking.testServices;

import com.example.demo.banking.dto.requests.ChangeDetailsRequest;
import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.dto.transformer.BalanceEnquiryTransformerImpl;
import com.example.demo.banking.dto.transformer.TransactionTransformerImpl;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UserServices;
import com.example.demo.banking.services.UtilityServices;
import com.example.demo.banking.services.implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
//@RequiredArgsConstructor
public class TestUserServices {

    @InjectMocks
    @Spy
    private UserServiceImplementation userServices;

    @Mock
    private TransactionTransformerImpl transactionTransformer;

    @Mock
    private UtilityServices utilityServices;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private BalanceEnquiryTransformerImpl balanceEnquiryTransformer;

    private TransactionRequest transactionRequest;
    private Transactions transaction;
    private Customer customer;

    @BeforeEach
    void setup(){
        transactionRequest= new TransactionRequest(1,100.0,"1234");
        customer=new Customer(1,9927L,"ABC","XYZ","FD",1000.0,"1234");
        transaction=new Transactions(1,new Date(), 100.0,100.0,customer);

    }


    @Test
    public void test_authenticate_correctPin()throws NoSuchAlgorithmException{

        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(utilityServices.md5Hasher(Mockito.anyString())).thenReturn(customer.getPin());

        assertEquals(true,userServices.authenticate(customer.getId(), customer.getPin()));
    }

    @Test
    public void test_authenticate_wrongPin()throws NoSuchAlgorithmException{

        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(utilityServices.md5Hasher(Mockito.anyString())).thenReturn("aaa");

        assertEquals(false,userServices.authenticate(customer.getId(), customer.getPin()));
    }

    @Test
    public void test_authenticate_NoSuchAlgoException()throws NoSuchAlgorithmException{
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(utilityServices.md5Hasher(Mockito.anyString())).thenThrow(NoSuchAlgorithmException.class);

        assertEquals(false,userServices.authenticate(Mockito.anyInt(),Mockito.anyString()));

    }

    @Test
    public void test_checkBalance()throws InvalidCredentialsException{
        BalanceEnquiryResponse response=new BalanceEnquiryResponse("ABC",100.0);
        EnquiryRequest request=new EnquiryRequest(1,"1234");
//        Mockito.when(userServices.authenticate(Mockito.anyInt(),Mockito.eq("1234"))).thenReturn(true);
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(balanceEnquiryTransformer.customerToBalanceEnquiry(Mockito.any(Customer.class))).thenReturn(response);
        BalanceEnquiryResponse test=userServices.checkBalance(request);
        System.out.println(test);
        assertEquals(response.getBalance(),test.getBalance());
    }

    @Test
    public void test_checkBalance_wrongPin()throws InvalidCredentialsException{
        BalanceEnquiryResponse response=new BalanceEnquiryResponse("ABC",100.0);
        EnquiryRequest request=new EnquiryRequest(1,"1234");
//        Mockito.when(userServices.authenticate(Mockito.anyInt(),Mockito.eq("1234"))).thenReturn(true);
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(balanceEnquiryTransformer.customerToBalanceEnquiry(Mockito.any(Customer.class))).thenReturn(response);
        try {
            BalanceEnquiryResponse test = userServices.checkBalance(request);
        }
        catch(InvalidCredentialsException ex){
            assertEquals("Invalid Credentials",ex.getMessage());
        }

    }

//    @Test
//    public void test_checkBalance_wrongPin()throws InvalidCredentialsException{
//        BalanceEnquiryResponse response=new BalanceEnquiryResponse("ABC",100.0);
//        EnquiryRequest request=new EnquiryRequest(1,"1234");
//        Mockito.when(userServices.authenticate(Mockito.anyInt(),Mockito.eq("1234"))).thenReturn(true);
//        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
//        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
//        Mockito.when(balanceEnquiryTransformer.customerToBalanceEnquiry(Mockito.any(Customer.class))).thenReturn(response);
//        BalanceEnquiryResponse test=userServices.checkBalance(request);
//        System.out.println(test);
//        assertEquals(response.getBalance(),test.getBalance());
//        assertThrows(InvalidCredentialsException.class,userServices.checkBalance(request));
//    }

    @Test
    public void test_deposit()throws InvalidCredentialsException{
        TransactionRequest request=new TransactionRequest(1,100.0,"1234");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transactions);
        Mockito.when(transactionTransformer.depositTransactionRequestToTransaction(request)).thenReturn(transactions);

        assertEquals("Transaction Successful",userServices.deposit(request));

    }
    @Test
    public void test_deposit_wrongPin(){
        TransactionRequest request=new TransactionRequest(1,100.0,"1234");
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transactions);
        Mockito.when(transactionTransformer.depositTransactionRequestToTransaction(request)).thenReturn(transactions);
        String msg;
        try{
            msg=userServices.deposit(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        assertEquals("Invalid Credentials",msg);

    }

    @Test
    public void test_withdraw(){
        TransactionRequest request=new TransactionRequest(1,100.0,"1234");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transactions);
        Mockito.when(transactionTransformer.withdrawTransactionRequestToTransaction(request)).thenReturn(transactions);
        String msg;
        try{
            msg=userServices.withdraw(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        catch(InsufficientBalanceException ex){
            msg=ex.getMessage();
        }
        assertEquals("Transaction Successful",msg);

    }

    @Test
    public void test_withdraw_wongPin(){
        TransactionRequest request=new TransactionRequest(1,100.0,"1234");
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transactions);
        Mockito.when(transactionTransformer.withdrawTransactionRequestToTransaction(request)).thenReturn(transactions);
        String msg;
        try{
            msg=userServices.withdraw(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        catch(InsufficientBalanceException ex){
            msg=ex.getMessage();
        }
        assertEquals("Invalid Credentials",msg);

    }
    @Test
    public void test_withdraw_InsufficientBalance(){
        TransactionRequest request=new TransactionRequest(1,100000.0,"1234");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transactions);
        Mockito.when(transactionTransformer.withdrawTransactionRequestToTransaction(request)).thenReturn(transactions);
        String msg;
        try{
            msg=userServices.withdraw(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        catch(InsufficientBalanceException ex){
            msg=ex.getMessage();
        }
        assertEquals("Insufficient Balance",msg);

    }

    @Test
    public void test_updatePin()throws NoSuchAlgorithmException{

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(utilityServices.md5Hasher(Mockito.anyString())).thenReturn("1111");
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updatePin(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        catch (NoSuchAlgorithmException ex){
            msg=ex.getMessage();
        }
        assertEquals("Pin changed successfully",msg);

    }
    @Test
    public void test_updatePin_wrongPin()throws NoSuchAlgorithmException{

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(utilityServices.md5Hasher(Mockito.anyString())).thenReturn("1111");
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updatePin(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        catch (NoSuchAlgorithmException ex){
            msg=ex.getMessage();
        }
        assertEquals("Invalid Credentials",msg);

    }

    @Test
    public void test_updatePhone(){

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updatePhone(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        assertEquals("Phone number updated",msg);

    }

    @Test
    public void test_updatePhone_wrongPin(){

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updatePhone(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        assertEquals("Invalid Credentials",msg);

    }

    @Test
    public void test_updateAddress(){

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updateAddress(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        assertEquals("Address updated",msg);

    }

    @Test
    public void test_updateAddress_wrongPin(){

        ChangeDetailsRequest request=new ChangeDetailsRequest(1,"1234","1111",0l,"XYZ");
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(customer);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        String msg;
        try{
            msg=userServices.updateAddress(request);
        }
        catch (InvalidCredentialsException ex){
            msg=ex.getMessage();
        }
        assertEquals("Invalid Credentials",msg);

    }

    @Test
    public void test_paginatedHistory(){
        EnquiryRequest request=new EnquiryRequest(1,"1234");
        List<TransactionResponse> response=new ArrayList<>();
        response.add(new TransactionResponse(new Date(),100.0,1000.0));
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        List<Transactions> transactionsList=new ArrayList<>();
        transactionsList.add(transactions);
        Page<Transactions> page=new PageImpl<Transactions>(transactionsList);
        Mockito.when(transactionRepo.findByCidPaged(Mockito.anyInt(),Mockito.any(Pageable.class))).thenReturn(page);
        Mockito.when(transactionTransformer.transactionsToTransactionResponse(Mockito.any(List.class))).thenReturn(response);
        List<TransactionResponse> actual;
        try{
            actual=userServices.paginatedHistory(request,0);

        }
        catch(InvalidCredentialsException ex){
            actual=new ArrayList<>();
        }
        assertEquals(actual.get(0).getAmount(),response.get(0).getAmount());
    }

    @Test
    public void test_paginatedHistory_wrongPin(){
        EnquiryRequest request=new EnquiryRequest(1,"1234");
        List<TransactionResponse> response=new ArrayList<>();
        response.add(new TransactionResponse(new Date(),100.0,1000.0));
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        List<Transactions> transactionsList=new ArrayList<>();
        transactionsList.add(transactions);
        Page<Transactions> page=new PageImpl<Transactions>(transactionsList);
        Mockito.when(transactionRepo.findByCidPaged(Mockito.anyInt(),Mockito.any(Pageable.class))).thenReturn(page);
        Mockito.when(transactionTransformer.transactionsToTransactionResponse(Mockito.any(List.class))).thenReturn(response);
        List<TransactionResponse> actual;
        try{
            actual=userServices.paginatedHistory(request,0);
        }
        catch(InvalidCredentialsException ex){
            actual=new ArrayList<>();
        }
        assertEquals(0,actual.size());
    }

    @Test
    public void test_history(){
        EnquiryRequest request=new EnquiryRequest(1,"1234");
        List<TransactionResponse> response=new ArrayList<>();
        response.add(new TransactionResponse(new Date(),100.0,1000.0));
        Mockito.doReturn(true).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        List<Transactions> transactionsList=new ArrayList<>();
        transactionsList.add(transactions);
        Mockito.when(transactionRepo.findByCId(Mockito.anyInt())).thenReturn(transactionsList);
        Mockito.when(transactionTransformer.transactionsToTransactionResponse(Mockito.any(List.class))).thenReturn(response);
        List<TransactionResponse> actual;
        try{
            actual=userServices.history(request);

        }
        catch(InvalidCredentialsException ex){
            actual=new ArrayList<>();
        }
        assertEquals(actual.get(0).getAmount(),response.get(0).getAmount());
    }

    @Test
    public void test_history_wrongPin(){
        EnquiryRequest request=new EnquiryRequest(1,"1234");
        List<TransactionResponse> response=new ArrayList<>();
        response.add(new TransactionResponse(new Date(),100.0,1000.0));
        Mockito.doReturn(false).when(userServices).authenticate(Mockito.anyInt(),Mockito.anyString());
        Transactions transactions=new Transactions(1,new Date(),100.0,100.0,customer);
        List<Transactions> transactionsList=new ArrayList<>();
        transactionsList.add(transactions);
        Mockito.when(transactionRepo.findByCId(Mockito.anyInt())).thenReturn(transactionsList);
        Mockito.when(transactionTransformer.transactionsToTransactionResponse(Mockito.any(List.class))).thenReturn(response);
        List<TransactionResponse> actual;
        try{
            actual=userServices.history(request);
        }
        catch(InvalidCredentialsException ex){
            actual=new ArrayList<>();
        }
        assertEquals(0,actual.size());
    }


}
