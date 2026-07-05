package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.atm.schema.ATMDepositEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AtmDepositEventMapper implements TransactionEventMapper<UpstreamAvroEvent<ATMDepositEvent>>{

    @Override
    public boolean supports(UpstreamEvent rawUpstreamEvent) {
        if (rawUpstreamEvent instanceof UpstreamAvroEvent<?> wrapper) {
            return wrapper.rawEvent() instanceof ATMDepositEvent;
        }
        return false;
    }

    @Override
    public RawTransaction map(UpstreamAvroEvent<ATMDepositEvent> source) {
        ATMDepositEvent event = source.rawEvent();

        return new RawTransaction.Builder()
                .accountId(event.getAccountId())
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .eventTimestamp(Instant.now())       // ATM events have no upstream timestamp
                .ingestionTimestamp(Instant.now())
                .sourceSystem(event.getNetwork())    // or event.getAtmId()
                .rawPayload(event.getRawPayload())
                .build();

    }
}
