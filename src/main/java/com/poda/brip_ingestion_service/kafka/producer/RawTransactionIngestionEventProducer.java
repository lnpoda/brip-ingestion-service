package com.poda.brip_ingestion_service.kafka.producer;

import com.poda.brip_ingestion_service.config.kafka.producer.topics.RawTransactionIngestionTopicConfigReader;
import com.poda.produce.schema.RawTransactionIngestionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RawTransactionIngestionEventProducer extends KafkaProducer<String, RawTransactionIngestionEvent>{

    private final RawTransactionIngestionTopicConfigReader rawTransactionIngestionTopicConfigReader;

    public RawTransactionIngestionEventProducer(@Qualifier("rawTransactionKafkaTemplate") KafkaTemplate<String, RawTransactionIngestionEvent> kafkaTemplate,
                                                RawTransactionIngestionTopicConfigReader rawTransactionIngestionTopicConfigReader) {
        super(kafkaTemplate);
        this.rawTransactionIngestionTopicConfigReader = rawTransactionIngestionTopicConfigReader;
    }

    public CompletableFuture<SendResult<String, RawTransactionIngestionEvent>> publish(RawTransactionIngestionEvent event) {
        return super.publish(rawTransactionIngestionTopicConfigReader.getTopic(), event.getRawTransactionId(), event);
    }
}
