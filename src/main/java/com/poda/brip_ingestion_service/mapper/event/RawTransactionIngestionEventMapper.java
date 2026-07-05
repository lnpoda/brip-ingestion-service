package com.poda.brip_ingestion_service.mapper.event;

import com.poda.brip_ingestion_service.entity.RawTransaction;
import com.poda.brip_ingestion_service.events.RawTransactionIngestionEvent;

public class RawTransactionIngestionEventMapper {

    public static RawTransactionIngestionEvent entityToEvent(RawTransaction entity) {
        return RawTransactionIngestionEvent.builder()
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
