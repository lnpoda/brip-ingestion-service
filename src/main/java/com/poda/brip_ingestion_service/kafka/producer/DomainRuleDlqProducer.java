package com.poda.brip_ingestion_service.kafka.producer;

import com.poda.brip_ingestion_service.model.dlq.RawTransactionDomainDlqEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainRuleDlqProducer {

    private final KafkaTemplate<String, RawTransactionDomainDlqEvent> domainRuleDlqKafkaTemplate;

    private static final String DOMAIN_DLQ_TOPIC = "raw-transactions.domain.dlq";

    public void publish(RawTransactionDomainDlqEvent event) {
        domainRuleDlqKafkaTemplate.send(DOMAIN_DLQ_TOPIC, event.transactionId(), event);
    }
}