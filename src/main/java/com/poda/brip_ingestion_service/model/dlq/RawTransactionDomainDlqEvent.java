package com.poda.brip_ingestion_service.model.dlq;

import com.poda.brip_ingestion_service.exception.domain.rules.DomainRuleViolationException;
import com.poda.brip_ingestion_service.model.RawTransaction;

import java.time.Instant;
import java.util.List;

public record RawTransactionDomainDlqEvent(
        String transactionId,
        RawTransaction rawTransaction,
        List<String> violations,
        Instant failedAt
) {}