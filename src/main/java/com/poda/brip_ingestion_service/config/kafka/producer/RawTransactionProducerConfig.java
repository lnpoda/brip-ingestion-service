package com.poda.brip_ingestion_service.config.kafka.producer;

import com.poda.brip_ingestion_service.events.RawTransactionIngestionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class RawTransactionProducerConfig {

    private final KafkaProperties kafkaProperties;

    public RawTransactionProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean(name = "rawTransactionProducerFactory")
    public ProducerFactory<String, RawTransactionIngestionEvent> producerFactory() {
        Map<String, Object> config = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "rawTransactionKafkaTemplate")
    public KafkaTemplate<String, RawTransactionIngestionEvent> kafkaTemplate(
            @Qualifier("rawTransactionProducerFactory")
            ProducerFactory<String, RawTransactionIngestionEvent> producerFactory) {

        return new KafkaTemplate<>(producerFactory);
    }
}
