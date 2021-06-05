package com.example.demo.banking.testControllers;

import com.example.demo.banking.controllers.UtilityController;
import com.example.demo.banking.dto.requests.CreateAccountRequest;
import com.example.demo.banking.entities.Customer;
import com.example.demo.banking.services.UtilityServices;
import com.example.demo.banking.services.implementation.UtilityServicesImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UtilityController.class)
@AutoConfigureMockMvc
public class TestUtilityController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UtilityServices utilityServices;

    @Test
    public void test_hello() throws Exception {
        String uri="/welcome";
//        MvcResult mvcResult=this.mockMvc
//                .perform(MockMvcRequestBuilders.get(uri))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        System.out.println(mvcResult);

        assertEquals(200,mvcResult.getResponse().getStatus());

    }

    @Test
    public void test_createUser() throws Exception {
        String uri="/createUser";

        CreateAccountRequest createAccountRequest=new CreateAccountRequest("ABC",9999L,"XYZ","FD","1234");
        Mockito.when(utilityServices.createUser(Mockito.any(CreateAccountRequest.class))).thenReturn("User "+createAccountRequest.getName()+" created");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());

    }

    @Test
    public void test_getById() throws Exception{
        String uri="/getById/1";
        Mockito.when(utilityServices.getCustomerById(Mockito.anyInt())).thenReturn(new Customer());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());

    }

}
