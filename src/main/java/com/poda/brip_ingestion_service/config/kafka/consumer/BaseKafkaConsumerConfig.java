package com.poda.brip_ingestion_service.config.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BaseKafkaConsumerConfig {

    public BaseKafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    private final KafkaProperties kafkaProperties;

    private KafkaTemplate<String, Object> dltKafkaTemplate;

    private ProducerFactory<String, Object> producerFactory;

    public ConsumerFactory<String, GenericRecord> createBaseConsumerFactory() {
        Map<String, Object> config = kafkaProperties.buildConsumerProperties();
//        standardAvroCustomizeConsumer(config, kafkaProperties);

        System.out.println("BOOTSTRAP SERVERS = " + config.get("bootstrap.servers"));
        System.out.println("SCHEMA REGISTRY = " + config.get("schema.registry.url"));

        // wrap the valueDeserializer with ErrorHandlingDeserializer
        Object valueDeserializer = config.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG);
        log.info("valueDeserializer: {}", valueDeserializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, valueDeserializer);

        log.info("resulting valueDeserializer: {}", config.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
        log.info("resulting ErrorHandlingDeserializer: {}", config.get(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS));


        return new DefaultKafkaConsumerFactory<>(config);
    }

    public ConcurrentKafkaListenerContainerFactory<String, GenericRecord>
    createBaseKafkaListenerContainerFactory(ConsumerFactory<String, GenericRecord> consumerFactory,
                                            KafkaTemplate<String, Object> dlqKafkaTemplate) {
        DefaultErrorHandler dlqErrorHandler = new DefaultErrorHandler(new DeadLetterPublishingRecoverer(dlqKafkaTemplate));
        ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(dlqErrorHandler);

        return factory;
    }

    public KafkaTemplate<String, Object> getOrCreateBaseDltKafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        if (dltKafkaTemplate == null) {
            dltKafkaTemplate = new KafkaTemplate<>(producerFactory);;
        }

        return dltKafkaTemplate;
    }

    public ProducerFactory<String, Object> getOrCreateProducerFactory() {

        if (producerFactory == null) {
            Map<String, Object> config = kafkaProperties.buildProducerProperties();
            producerFactory = new DefaultKafkaProducerFactory<>(config);
        }

        return producerFactory;
    }
}
