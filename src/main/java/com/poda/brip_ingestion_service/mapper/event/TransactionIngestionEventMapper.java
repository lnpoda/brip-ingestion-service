package com.poda.brip_ingestion_service.mapper.event;

import com.poda.brip_ingestion_service.entity.Transaction;
import com.poda.brip_ingestion_service.events.TransactionIngestionEvent;
import com.poda.brip_ingestion_service.exception.AccountNotFoundWithAccountNumber;
import com.poda.brip_ingestion_service.repository.AccountRepository;

import static com.poda.brip_ingestion_service.utils.Currency.getCodeFromCurrency;

public class TransactionIngestionEventMapper {

    public static TransactionIngestionEvent entityToEvent(Transaction entity, TransactionIngestionEvent event) {
        event.setTransactionReferenceId(entity.getTransactionReferenceId());
        event.setAmount(entity.getAmount());
        event.setTransactionChannel(entity.getChannel());
        event.setFees(entity.getFees());
        event.setTransactionStatus(entity.getTransactionStatus());
        event.setNetAmount(entity.getNetAmount());
        event.setTransactionType(entity.getTransactionType());
        event.setSenderAccountNumber(entity.getSenderAccount().getAccountNumber());
        event.setReceiverAccountNumber(entity.getReceiverAccount().getAccountNumber());

        return event;
    }

    public static Transaction eventToEntity(TransactionIngestionEvent event, Transaction entity, AccountRepository accountRepository) {
        entity.setTransactionReferenceId(event.getTransactionReferenceId());
        entity.setAmount(event.getAmount());
        entity.setChannel(event.getTransactionChannel());
        entity.setFees(event.getFees());
        entity.setTransactionType(event.getTransactionType());
        entity.setTransactionStatus(event.getTransactionStatus());
        entity.setTransactionCurrencyCode(getCodeFromCurrency(""));
        entity.setReceiverAccount(accountRepository.findByAccountNumber(event.getReceiverAccountNumber())
                .orElseThrow(()->new AccountNotFoundWithAccountNumber(event.getReceiverAccountNumber())));
        entity.setSenderAccount(accountRepository.findByAccountNumber(event.getReceiverAccountNumber())
                .orElseThrow(()->new AccountNotFoundWithAccountNumber(event.getSenderAccountNumber())));

        return entity;
    }
}
