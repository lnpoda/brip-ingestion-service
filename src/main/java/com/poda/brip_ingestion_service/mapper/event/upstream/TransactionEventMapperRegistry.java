package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.brip_ingestion_service.events.upstream.UpstreamEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransactionEventMapperRegistry {

    private final List<TransactionEventMapper<?>> transactionEventMappers;

    public TransactionEventMapper<?> findMapper(UpstreamEvent transactionEvent) {
        return this.transactionEventMappers.stream()
                .filter(mapper-> mapper.supports(transactionEvent))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("no transactionEventMapper found for event type: "
                        + transactionEvent.getClass().getName()));
    }
}
