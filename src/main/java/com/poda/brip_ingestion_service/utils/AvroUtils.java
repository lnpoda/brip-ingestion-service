package com.poda.brip_ingestion_service.utils;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AvroUtils {

    public static Map<String, Object> standardAvroCustomizeConsumer(Map<String, Object> consumerConfig,
                                                                    KafkaProperties kafkaProperties) {
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        consumerConfig.put("specific.avro.reader", false);
//        consumerConfig.put("schema.registry.url", kafkaProperties.getProperties().get("schema.registry.url"));

        return consumerConfig;
    }

    public static SpecificRecord convertGenericToSpecificRecord(GenericRecord record) throws IOException {
        Schema schema = record.getSchema();

        DatumReader<SpecificRecord> reader = new SpecificDatumReader<>(schema, schema, new SpecificData());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = new EncoderFactory().binaryEncoder(out, null);
        GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);

        writer.write(record, encoder);
        encoder.flush();

        Decoder decoder = new DecoderFactory().binaryDecoder(out.toByteArray(), null);

        return reader.read(null, decoder);
    }

    public static String specificRecordToJson(SpecificRecord record) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            DatumWriter<SpecificRecord> writer =
                    new SpecificDatumWriter<>(record.getSchema());

            Encoder encoder =
                    EncoderFactory.get().jsonEncoder(record.getSchema(), out);

            writer.write(record, encoder);
            encoder.flush();

            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Avro SpecificRecord to JSON", e);
        }
    }

}
