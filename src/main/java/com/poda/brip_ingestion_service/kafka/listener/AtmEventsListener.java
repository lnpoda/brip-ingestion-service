package com.poda.brip_ingestion_service.kafka.listener;

import com.poda.brip_ingestion_service.mapper.event.upstream.UpstreamEventProcessor;
import com.poda.brip_ingestion_service.model.RawTransaction;
import com.poda.brip_ingestion_service.service.RawTransactionIngestionService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class AtmEventsListener {

    private final RawTransactionIngestionService rawTransactionIngestionService;

    private final UpstreamEventProcessor upstreamEventProcessor;



    @KafkaListener(
            topics = "${upstream.atm.topic}",
            containerFactory = "AtmEventsKafkaListenerContainerFactory"
    )
    public void handle(GenericRecord record) {

        log.info("genericRecord: {}", record);

       RawTransaction rawTransaction = upstreamEventProcessor.standardProcessUpstreamEvent(record);
       rawTransactionIngestionService.ingestRawTransaction(rawTransaction);

    }

    @PostConstruct
    public void init() {
        log.info("ATM Listener bean created");
    }

}
