package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.atm.schema.ATMWithdrawalEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AtmWithdrawalEventMapper implements TransactionEventMapper<UpstreamAvroEvent<ATMWithdrawalEvent>>{

    @Override
    public boolean supports(UpstreamEvent rawUpstreamEvent) {
        if (rawUpstreamEvent instanceof UpstreamAvroEvent<?> wrapper) {
            return wrapper.rawEvent() instanceof ATMWithdrawalEvent;
        }
        return false;
    }

    @Override
    public RawTransaction map(UpstreamAvroEvent<ATMWithdrawalEvent> source) {
        ATMWithdrawalEvent event = source.rawEvent();

        return new RawTransaction.Builder()
                .accountId(event.getAccountId())
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .eventTimestamp(Instant.now())        // ATM events have no timestamp
                .ingestionTimestamp(Instant.now())
                .sourceSystem(event.getNetwork())
                .rawPayload(event.getRawPayload())// or event.getAtmId()
                .build();
    }

}
