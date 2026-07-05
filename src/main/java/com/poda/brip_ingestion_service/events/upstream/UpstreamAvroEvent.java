package com.poda.brip_ingestion_service.events.upstream;

import org.apache.avro.specific.SpecificRecord;

public record UpstreamAvroEvent<T extends SpecificRecord>(T rawEvent) implements UpstreamEvent {
}
