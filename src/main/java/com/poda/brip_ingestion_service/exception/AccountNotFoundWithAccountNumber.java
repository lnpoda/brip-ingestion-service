package com.poda.brip_ingestion_service.exception;

public class AccountNotFoundWithAccountNumber extends AccountNotFoundException{

    public AccountNotFoundWithAccountNumber(String accountNumber) {
        super("accountNumber", accountNumber);
    }
}
