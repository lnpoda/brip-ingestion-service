package com.poda.brip_ingestion_service.service;

import com.poda.brip_ingestion_service.events.RawTransactionIngestionDomainEvent;
import com.poda.brip_ingestion_service.exception.domain.rules.DomainRuleViolationException;
import com.poda.brip_ingestion_service.kafka.producer.DomainRuleDlqProducer;
import com.poda.brip_ingestion_service.kafka.producer.RawTransactionIngestionEventProducer;
import com.poda.brip_ingestion_service.mapper.RawTransactionDomainDlqEventMapper;
import com.poda.brip_ingestion_service.mapper.event.RawTransactionIngestionDomainEventMapper;
import com.poda.brip_ingestion_service.mapper.event.downstream.RawTransactionIngestionEventAvroMapper;
import com.poda.brip_ingestion_service.mapper.model.RawTransactionMapper;
import com.poda.brip_ingestion_service.model.RawTransaction;
import com.poda.brip_ingestion_service.model.dlq.RawTransactionDomainDlqEvent;
import com.poda.brip_ingestion_service.repository.RawTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RawTransactionIngestionService {

    private final DomainRulesService domainRulesService;

    private final RawTransactionIngestionEventProducer rawTransactionIngestionEventProducer;

    private final DomainRuleDlqProducer domainRuleDlqProducer;

    private final RawTransactionRepository rawTransactionRepository;


    public void ingestRawTransaction(RawTransaction rawTransaction) {

        List<DomainRuleViolationException> domainRuleViolations = domainRulesService.applyIngestionRules(rawTransaction);


        if (!domainRuleViolations.isEmpty()) {
            domainRuleViolations.forEach(rule ->log.error(
                    "Domain rule violation: [{}] {}", rule.getClass().getSimpleName(), rule.toString())
            );

            log.error("RawTransaction {} broke domain rules and is prevented from ingestion",rawTransaction.getRawTransactionId());

            RawTransactionDomainDlqEvent dlqEvent = RawTransactionDomainDlqEventMapper.toDlqEvent(rawTransaction,
                    domainRuleViolations);

            domainRuleDlqProducer.publish(dlqEvent);

            log.error("RawTransaction {} sent to DOMAIN DLQ due to rule violations",
                    rawTransaction.getRawTransactionId());
            return;
        }

        // Save rawTransactionEntity to db
        com.poda.brip_ingestion_service.entity.RawTransaction rawTransactionEntity =
                RawTransactionMapper.modelToEntity(rawTransaction,
                        new com.poda.brip_ingestion_service.entity.RawTransaction());
        rawTransactionRepository.save(rawTransactionEntity);

        log.info("rawTransaction: {}", rawTransaction);
        // reuse the rawTransactionEntity saved to db, to create event and publish to kafka
        RawTransactionIngestionDomainEvent rawTransactionIngestionDomainEvent = RawTransactionIngestionDomainEventMapper
                .entityToEvent(rawTransactionEntity);
        rawTransactionIngestionEventProducer.publish(RawTransactionIngestionEventAvroMapper.toAvro(rawTransactionIngestionDomainEvent))
                .whenComplete((result, error) -> {
                    if (error != null) {
                        log.error(error.toString());
                    }
                    log.info(result.getProducerRecord().toString());
                });
    }
}
