package com.diego.bank_api.exception;

public class EmailAlreadyExistsException extends BusinessException{
    public EmailAlreadyExistsException(){
        super("Email already exist");
    }
}
