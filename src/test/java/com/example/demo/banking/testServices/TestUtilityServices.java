package com.example.demo.banking.testServices;

import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformerImpl;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.entities.Transactions;
import com.example.demo.banking.repositories.CustomerRepo;
import com.example.demo.banking.repositories.TransactionRepo;
import com.example.demo.banking.services.UtilityServices;
import com.example.demo.banking.services.implementation.UtilityServicesImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TestUtilityServices {
    @InjectMocks
    UtilityServicesImplementation utilityServices;

    @Mock
    CustomerRepo customerRepo;

    @Mock
    TransactionRepo transactionRepo;

    @Mock
    CreateAccountTransformerImpl createAccountTransformer;

    private CreateAccountRequest request;
    private Customer customer;
    private Transactions transaction;
    @BeforeEach
    void setup(){
        customer =new Customer(1,99999999L,"ABC","address","FD",100.0,"81dc9bdb52d04dc20036dbd8313ed055");
        request=new CreateAccountRequest("ABC",9999999L,"XYZ","FD","1234");
        transaction=new Transactions(1,new Date(),10.0,90.0,customer);
    }

    @Test
    public void test_md5Hasher(){

        try{
            String expected="81dc9bdb52d04dc20036dbd8313ed055";
            String hash=utilityServices.md5Hasher("1234");
            assertEquals(expected,hash);
        }catch(NoSuchAlgorithmException ex){
            assert false;
        }
    }

    @Test
    public void test_getCustomerById(){

            Integer id = 1;

            Mockito.when(customerRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));

            Customer result = utilityServices.getCustomerById(Mockito.anyInt());

            assertEquals(customer.getName(), result.getName());

    }

    @Test
    public void test_createUser(){

        Mockito.when(createAccountTransformer.createAccountRequestToModel(request)).thenReturn(customer);
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);


        assertEquals("User "+customer.getName()+" created",utilityServices.createUser(request));

    }

    @Test
    public void test_deductRDBalance(){

        List<Customer> customerList=new ArrayList<>();
        customerList.add(customer);
        Mockito.when(customerRepo.getByAccountType("RD")).thenReturn(customerList);
        Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(transactionRepo.save(Mockito.any(Transactions.class))).thenReturn(transaction);

        assertEquals("RD Balance deducted",utilityServices.deductRDBalance());
    }

}
