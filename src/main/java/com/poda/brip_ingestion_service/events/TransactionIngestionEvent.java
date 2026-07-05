package com.poda.brip_ingestion_service.events;

import com.poda.brip_ingestion_service.constants.Channel;
import com.poda.brip_ingestion_service.constants.TransactionStatus;
import com.poda.brip_ingestion_service.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TransactionIngestionEvent {

    private String transactionReferenceId;

    private TransactionType transactionType;

    private Channel transactionChannel;

    private TransactionStatus transactionStatus;

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal netAmount;

    private BigDecimal currency;
}
