package com.poda.brip_ingestion_service.mapper;

import com.poda.brip_ingestion_service.exception.domain.rules.DomainRuleViolationException;
import com.poda.brip_ingestion_service.model.RawTransaction;
import com.poda.brip_ingestion_service.model.dlq.RawTransactionDomainDlqEvent;

import java.time.Instant;
import java.util.List;

public class RawTransactionDomainDlqEventMapper {

    public static RawTransactionDomainDlqEvent toDlqEvent(
            RawTransaction tx,
            List<DomainRuleViolationException> violations
    ) {
        return new RawTransactionDomainDlqEvent(
                tx.getRawTransactionId(),
                tx,
                violations.stream().map(Throwable::getMessage).toList(),
                Instant.now()
        );
    }
}