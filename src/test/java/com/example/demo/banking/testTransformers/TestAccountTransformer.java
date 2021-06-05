package com.example.demo.banking.testTransformers;


import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.dto.transformer.CreateAccountTransformerImpl;
import com.example.demo.banking.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestAccountTransformer {

    @Autowired
    CreateAccountTransformerImpl transformer;

    @Test
    public void test_createAccountRequestToModel(){

        Customer customer=new Customer(1,9999L,"ABC","XYZ","FD",0.0,"1234");
        CreateAccountRequest request=new CreateAccountRequest("ABC",9999L,"XYZ","FD","1234");
        assertEquals(customer.getName(),transformer.createAccountRequestToModel(request).getName());
    }
}
