package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;
import com.poda.corebanking.schema.AccountCreditPostedEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountCreditPostedEventMapper implements TransactionEventMapper<UpstreamAvroEvent<AccountCreditPostedEvent>>{

    @Override
    public boolean supports(UpstreamEvent rawUpstreamEvent) {
        if (rawUpstreamEvent instanceof UpstreamAvroEvent<?> wrapper) {
            return wrapper.rawEvent() instanceof AccountCreditPostedEvent;
        }
        return false;
    }

    @Override
    public RawTransaction map(UpstreamAvroEvent<AccountCreditPostedEvent> source) {
        AccountCreditPostedEvent event = source.rawEvent();

        return new RawTransaction.Builder()
                .accountId(event.getAccountId())
                .amount(event.getAmount())
                .currency(event.getCurrency())
                .eventTimestamp(event.getPostingTimestamp())
                .counterpartyAccountId(event.getCounterpartyAccountId())
                .sourceSystem(event.getChannel())
                .rawPayload(event.getRawPayload())
                .build();
    }
}
