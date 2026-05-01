package com.diego.bank_api.exception;

public class DocumentAlreadyExistsException extends BusinessException{
    public DocumentAlreadyExistsException(){
        super("Document already exist!");
    }
}
