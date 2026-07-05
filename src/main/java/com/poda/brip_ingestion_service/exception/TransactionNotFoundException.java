package com.poda.brip_ingestion_service.exception;

public class TransactionNotFoundException extends ResourceNotFoundException{

    public TransactionNotFoundException(String field, String value) {
        super("transaction", field, value);
    }
}
