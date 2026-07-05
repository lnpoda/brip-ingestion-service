package com.poda.brip_ingestion_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RawTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rawTransactionId;

    private String sourceSystem;
    private String sourceEventType;


    private String accountId;
    private String counterpartyAccountId;


    private String currency;
    private BigDecimal amount;


    private Instant eventTimestamp;
    private Instant ingestionTimestamp;
    @Lob
    private String rawPayload;

    private Integer partition;
    private Long offset;
    private String correlationId;
    private String eventId;

}
