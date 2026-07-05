package com.poda.brip_ingestion_service.kafka.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
//@Component
public class KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public CompletableFuture<SendResult<K, V>> publish(String topic, K key, V message) {
        return kafkaTemplate.send(topic, key, message);
    }
}
