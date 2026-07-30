package com.poda.brip_ingestion_service.config.kafka.producer.topics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @Setter
@ConfigurationProperties(prefix = "downstream.raw-transaction-ingestion-topic")
public class RawTransactionIngestionTopicConfigReader {
    String topic;
}
