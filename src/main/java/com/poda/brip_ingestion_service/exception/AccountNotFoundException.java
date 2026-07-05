package com.poda.brip_ingestion_service.exception;

public class AccountNotFoundException extends ResourceNotFoundException{

    public AccountNotFoundException(String field, String value) {
        super("account", field, value);
    }
}
