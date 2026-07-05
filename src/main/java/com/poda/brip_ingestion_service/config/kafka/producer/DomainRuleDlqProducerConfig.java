package com.poda.brip_ingestion_service.config.kafka.producer;

import com.poda.brip_ingestion_service.model.dlq.RawTransactionDomainDlqEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class DomainRuleDlqProducerConfig {

    @Bean
    public KafkaTemplate<String, RawTransactionDomainDlqEvent> domainRuleDlqKafkaTemplate(
            ProducerFactory<String, RawTransactionDomainDlqEvent> pf
    ) {
        return new KafkaTemplate<>(pf);
    }
}