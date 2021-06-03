package com.example.demo.banking.dto.requests;

import lombok.Data;

@Data
public class EnquiryRequest {
    private Integer userId;
    private String pin;
}
