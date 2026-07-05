package com.poda.brip_ingestion_service.mapper.event.upstream;

import com.poda.brip_ingestion_service.events.upstream.UpstreamAvroEvent;
import com.poda.brip_ingestion_service.model.RawTransaction;
import lombok.AllArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.poda.brip_ingestion_service.utils.AvroUtils.convertGenericToSpecificRecord;

@Component
@AllArgsConstructor
public class UpstreamEventProcessor {

    private final TransactionEventMapperRegistry mapperRegistry;

    public RawTransaction standardProcessUpstreamEvent(GenericRecord record) {
        try {
            SpecificRecord specificRecord = convertGenericToSpecificRecord(record);
            UpstreamAvroEvent<?> wrappedEvent = new UpstreamAvroEvent<>(specificRecord);
            TransactionEventMapper<?> eventMapper = mapperRegistry.findMapper(wrappedEvent);
//            eventMapper.map(wrappedEvent);
            return invokeMapper(eventMapper, wrappedEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends UpstreamAvroEvent<?>> RawTransaction invokeMapper(TransactionEventMapper<T> mapper,
                                                                         UpstreamAvroEvent<?> event) {
        @SuppressWarnings("unchecked")
        T typed = (T) event;
        return mapper.map(typed);
    }
}
