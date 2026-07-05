package com.poda.brip_ingestion_service.kafka.producer;

import com.poda.brip_ingestion_service.events.RawTransactionIngestionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RawTransactionIngestionEventProducer extends KafkaProducer<String, RawTransactionIngestionEvent>{

    public RawTransactionIngestionEventProducer(@Qualifier("rawTransactionKafkaTemplate") KafkaTemplate<String, RawTransactionIngestionEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public CompletableFuture<SendResult<String, RawTransactionIngestionEvent>> publish(RawTransactionIngestionEvent event) {
        return super.publish("transaction-ingestion-topic", event.getRawTransactionId(), event);
    }
}
