package com.poda.brip_ingestion_service.mapper.event.downstream;

import com.poda.brip_ingestion_service.events.RawTransactionIngestionDomainEvent;
import com.poda.produce.schema.RawTransactionIngestionEvent;

public class RawTransactionIngestionEventAvroMapper {

    public static RawTransactionIngestionEvent toAvro(RawTransactionIngestionDomainEvent domain) {

        RawTransactionIngestionEvent.Builder builder = RawTransactionIngestionEvent.newBuilder();

        builder.setRawTransactionId(domain.getRawTransactionId());
        builder.setSourceSystem(domain.getSourceSystem());
        builder.setSourceEventType(domain.getSourceEventType());

        // BigDecimal → BigDecimal (no conversion needed)
        builder.setAmount(domain.getAmount());

        builder.setCurrency(domain.getCurrency());
        builder.setAccountId(domain.getAccountId());
        builder.setCounterpartyAccountId(domain.getCounterpartyAccountId());

        // Instant → Instant (no conversion needed)
        builder.setEventTimestamp(domain.getEventTimestamp());
        builder.setIngestionTimestamp(domain.getIngestionTimestamp());

        builder.setRawPayload(domain.getRawPayload());

        builder.setPartition(domain.getPartition());
        builder.setOffset(domain.getOffset());

        builder.setCorrelationId(domain.getCorrelationId());
        builder.setEventId(domain.getEventId());

        return builder.build();
    }
}
