package com.poda.brip_ingestion_service.exception;

public class TransactionNotFoundWithReferenceId extends TransactionNotFoundException {

    public TransactionNotFoundWithReferenceId(String transactionReferenceId) {
        super("transactionReferenceId", transactionReferenceId);
    }
}
