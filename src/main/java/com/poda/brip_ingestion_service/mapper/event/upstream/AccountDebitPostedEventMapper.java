package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;
import com.poda.corebanking.schema.AccountDebitPostedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class AccountDebitPostedEventMapper implements TransactionEventMapper<UpstreamAvroEvent<AccountDebitPostedEvent>>{

    @Override
    public boolean supports(UpstreamEvent rawUpstreamEvent) {
        if (rawUpstreamEvent instanceof UpstreamAvroEvent<?> wrapper) {
            return wrapper.rawEvent() instanceof AccountDebitPostedEvent;
        }
        return false;
    }

    @Override
    public RawTransaction map(UpstreamAvroEvent<AccountDebitPostedEvent> source) {
        AccountDebitPostedEvent event = source.rawEvent();

        BigDecimal amount = event.getAmount();

        return new RawTransaction.Builder()
                .accountId(event.getAccountId())
                .amount(amount)
                .currency(event.getCurrency())
                .counterpartyAccountId(event.getCounterpartyAccountId())
                .eventTimestamp(event.getPostingTimestamp())   // authoritative ledger time
                .ingestionTimestamp(Instant.now())
                .sourceSystem(event.getChannel())
                .rawPayload(event.getRawPayload())
                .build();
    }
}
