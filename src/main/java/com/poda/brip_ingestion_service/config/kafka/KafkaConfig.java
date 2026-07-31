package com.poda.brip_ingestion_service.config.kafka;

import com.poda.brip_ingestion_service.config.kafka.producer.topics.RawTransactionIngestionTopicConfigReader;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@AllArgsConstructor
@Configuration
public class KafkaConfig {

    private final RawTransactionIngestionTopicConfigReader rawTransactionIngestionTopicConfigReader;

    @Bean(name = "rawTransactionIngestionTopic")
    public NewTopic createRawTransactionIngestionTopic() {
        return TopicBuilder.name(rawTransactionIngestionTopicConfigReader.getTopic())
                .build();
    }
}
