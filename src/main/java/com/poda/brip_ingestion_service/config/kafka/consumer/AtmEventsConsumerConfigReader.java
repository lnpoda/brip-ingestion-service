package com.poda.brip_ingestion_service.config.kafka.consumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "upstream.atm")
public class AtmEventsConsumerConfigReader {

    private String topic;

    private int concurrency;
}
