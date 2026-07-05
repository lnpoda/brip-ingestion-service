package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;

public interface TransactionEventMapper<T extends UpstreamAvroEvent<?>> {

    public boolean supports (UpstreamEvent event);

    public RawTransaction map(T source);
}
