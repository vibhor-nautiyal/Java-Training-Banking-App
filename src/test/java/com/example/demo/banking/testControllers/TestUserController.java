package com.example.demo.banking.testControllers;


import com.example.demo.banking.controllers.UserController;
import com.example.demo.banking.dto.requests.ChangeDetailsRequest;
import com.example.demo.banking.dto.requests.EnquiryRequest;
import com.example.demo.banking.dto.requests.TransactionRequest;
import com.example.demo.banking.dto.response.BalanceEnquiryResponse;
import com.example.demo.banking.dto.response.TransactionResponse;
import com.example.demo.banking.exceptions.InsufficientBalanceException;
import com.example.demo.banking.exceptions.InvalidCredentialsException;
import com.example.demo.banking.services.UserServices;
import com.example.demo.banking.services.UtilityServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserServices userServices;

    @Test
    public void test_deposit_success() throws Exception{

        String uri="/user/deposit";

        Mockito.when(userServices.deposit(Mockito.any(TransactionRequest.class))).thenReturn("Transaction Successful");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new TransactionRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_deposit_failed() throws Exception{

        String uri="/user/deposit";

        Mockito.when(userServices.deposit(Mockito.any(TransactionRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new TransactionRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_withdraw_success() throws Exception{

        String uri="/user/withdraw";
        Mockito.when(userServices.withdraw(Mockito.any(TransactionRequest.class))).thenReturn("Transaction Successful");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new TransactionRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }


    @Test
    public void test_withdraw_failed() throws Exception{

        String uri="/user/withdraw";
        Mockito.when(userServices.withdraw(Mockito.any(TransactionRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new TransactionRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_withdraw_failed2() throws Exception{

        String uri="/user/withdraw";
        Mockito.when(userServices.withdraw(Mockito.any(TransactionRequest.class))).thenThrow(InsufficientBalanceException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new TransactionRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_checkBalance_success() throws Exception{
        String uri="/user/checkBalance";
        Mockito.when(userServices.checkBalance(Mockito.any(EnquiryRequest.class))).thenReturn(new BalanceEnquiryResponse());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_checkBalance_failed() throws Exception{
        String uri="/user/checkBalance";
        Mockito.when(userServices.checkBalance(Mockito.any(EnquiryRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_history_success() throws Exception{
        String uri="/user/history";
        Mockito.when(userServices.history(Mockito.any(EnquiryRequest.class))).thenReturn(new ArrayList<TransactionResponse>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_history_failed() throws Exception{
        String uri="/user/history";
        Mockito.when(userServices.history(Mockito.any(EnquiryRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }


    @Test
    public void test_getPaginatedHistory_success() throws Exception{
        String uri="/user/getPaginatedHistory/0";

        Mockito.when(userServices.paginatedHistory(Mockito.any(EnquiryRequest.class),Mockito.anyInt())).thenReturn(new ArrayList<TransactionResponse>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_getPaginatedHistory_failed() throws Exception{
        String uri="/user/getPaginatedHistory/0";
        Mockito.when(userServices.paginatedHistory(Mockito.any(EnquiryRequest.class),Mockito.anyInt())).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changePin_success() throws Exception{
        String uri="/user/changePin";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenReturn("Pin changed successfully");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changePin_failed() throws Exception{
        String uri="/user/changePin";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changePin_failed2() throws Exception{
        String uri="/user/changePin";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenThrow(NoSuchAlgorithmException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changePhone_success() throws Exception{
        String uri="/user/changePhone";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenReturn("Pin changed successfully");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changePhone_failed() throws Exception{
        String uri="/user/changePhone";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changeAddress_success() throws Exception{
        String uri="/user/changeAddress";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenReturn("Pin changed successfully");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_changeAddress_failed() throws Exception{
        String uri="/user/changeAddress";
        Mockito.when(userServices.updatePin(Mockito.any(ChangeDetailsRequest.class))).thenThrow(InvalidCredentialsException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new EnquiryRequest())))
                .andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
    }

}
