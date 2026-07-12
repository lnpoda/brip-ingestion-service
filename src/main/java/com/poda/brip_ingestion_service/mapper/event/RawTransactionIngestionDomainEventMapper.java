package com.poda.brip_ingestion_service.mapper.event;

import com.poda.brip_ingestion_service.entity.RawTransaction;
import com.poda.brip_ingestion_service.events.RawTransactionIngestionDomainEvent;

public class RawTransactionIngestionDomainEventMapper {

    public static RawTransactionIngestionDomainEvent entityToEvent(RawTransaction entity) {
        return RawTransactionIngestionDomainEvent.builder()
                .rawTransactionId(entity.getRawTransactionId())
                .sourceSystem(entity.getSourceSystem())
                .sourceEventType(entity.getSourceEventType())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .accountId(entity.getAccountId())
                .counterpartyAccountId(entity.getCounterpartyAccountId())
                .eventTimestamp(entity.getEventTimestamp())
                .ingestionTimestamp(entity.getIngestionTimestamp())
                .rawPayload(entity.getRawPayload())   // already JSON
                .partition(entity.getPartition())
                .offset(entity.getOffset())
                .correlationId(entity.getCorrelationId())
                .eventId(entity.getEventId())
                .build();
    }
}
