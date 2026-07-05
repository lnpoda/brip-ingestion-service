package com.poda.brip_ingestion_service.config.kafka.consumer;


import lombok.AllArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@AllArgsConstructor
@Configuration
public class AtmEventsKafkaConsumerConfig {


    private final BaseKafkaConsumerConfig baseKafkaConsumerConfig;

    @Bean(name = "AtmEventsConsumerFactory")
    public ConsumerFactory<String, GenericRecord> consumerFactory() {

        return baseKafkaConsumerConfig.createBaseConsumerFactory();
    }

    @Bean(name = "AtmEventsKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, GenericRecord>
    kafkaListenerContainerFactory(@Qualifier("AtmEventsConsumerFactory") ConsumerFactory<String, GenericRecord> consumerFactory,
                                  @Qualifier("AtmEventsDltKafkaTemplate")KafkaTemplate<String, Object> dltKafkaTemplate) {

        return baseKafkaConsumerConfig.createBaseKafkaListenerContainerFactory(consumerFactory, dltKafkaTemplate);
    }

    @Bean(name="AtmEventsDltKafkaTemplate")
    public KafkaTemplate<String, Object> dlqKafkaTemplate(@Qualifier("AtmEventsProducerFactory") ProducerFactory<String, Object> producerFactory) {

        return baseKafkaConsumerConfig.getOrCreateBaseDltKafkaTemplate(producerFactory);
    }

    @Bean("AtmEventsProducerFactory")
    public ProducerFactory<String, Object> producerFactory() {

        return baseKafkaConsumerConfig.getOrCreateProducerFactory();
    }
}
