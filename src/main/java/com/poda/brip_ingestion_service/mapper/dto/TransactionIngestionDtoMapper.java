package com.poda.brip_ingestion_service.mapper.dto;

import com.poda.brip_ingestion_service.constants.TransactionStatus;
import com.poda.brip_ingestion_service.dto.TransactionIngestionDto;
import com.poda.brip_ingestion_service.entity.Transaction;
import com.poda.brip_ingestion_service.exception.AccountNotFoundWithAccountNumber;
import com.poda.brip_ingestion_service.repository.AccountRepository;

import static com.poda.brip_ingestion_service.utils.Currency.getCodeFromCurrency;
import static com.poda.brip_ingestion_service.utils.Currency.getCurrencyFromCode;

public class TransactionIngestionDtoMapper {

    public static Transaction dtoToEntity(TransactionIngestionDto dto, Transaction entity, AccountRepository accountRepository) {
        entity.setAmount(dto.getAmount());
        entity.setChannel(dto.getTransactionChannel());
        entity.setFees(dto.getFees());
        entity.setNetAmount(dto.getNetAmount());

        entity.setSenderAccount(accountRepository
                .findByAccountNumber(dto.getSenderAccountNumber())
                .orElseThrow(()->new AccountNotFoundWithAccountNumber(dto.getSenderAccountNumber())));

        entity.setReceiverAccount(accountRepository
                .findByAccountNumber(dto.getSenderAccountNumber())
                .orElseThrow(()->new AccountNotFoundWithAccountNumber(dto.getReceiverAccountNumber())));

        entity.setTransactionCurrencyCode(getCodeFromCurrency("")); //TODO: change this to accept code converted from actual currency
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionStatus(TransactionStatus.INITIATED);

        return entity;
    }

    public static TransactionIngestionDto entityToDto(Transaction entity, TransactionIngestionDto dto) {
        dto.setAmount(entity.getAmount());
        dto.setTransactionChannel(entity.getChannel());
        dto.setFees(entity.getFees());
        dto.setNetAmount(entity.getNetAmount());
        dto.setReceiverAccountNumber(entity.getReceiverAccount().getAccountNumber());
        dto.setSenderAccountNumber(entity.getSenderAccount().getAccountNumber());
        dto.setCurrency(getCurrencyFromCode(""));
        dto.setTransactionType(entity.getTransactionType());
        dto.setTransactionStatus(entity.getTransactionStatus());


        return dto;
    }


}
