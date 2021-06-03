package com.example.demo.banking.exceptions;

public class InvalidCredentialsException extends Exception{

    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
