package com.poda.brip_ingestion_service.mapper.model;

import com.poda.brip_ingestion_service.entity.RawTransaction;
import org.apache.avro.specific.SpecificRecord;

import static com.poda.brip_ingestion_service.utils.AvroUtils.specificRecordToJson;

public class RawTransactionMapper {

    public static RawTransaction modelToEntity(com.poda.brip_ingestion_service.model.RawTransaction model,
                                               RawTransaction entity) {
        entity.setRawTransactionId(model.getRawTransactionId());
        entity.setCurrency(model.getCurrency());
        entity.setAmount(model.getAmount());

        entity.setAccountId(model.getAccountId());
        entity.setCounterpartyAccountId(model.getCounterpartyAccountId());
        entity.setEventTimestamp(model.getEventTimestamp());
        entity.setIngestionTimestamp(model.getIngestionTimestamp());
        entity.setSourceEventType(model.getSourceEventType());
        entity.setSourceSystem(model.getSourceSystem());

//        if (!(model.getRawPayload() instanceof SpecificRecord)) {
//            throw new IllegalArgumentException(
//                    "RawTransaction.rawPayload must be a SpecificRecord at ingestion time"
//            );
//        }

        String rawPayloadJson = specificRecordToJson((SpecificRecord) model.getRawPayload());
        entity.setRawPayload(rawPayloadJson);

        return entity;
    }
}
