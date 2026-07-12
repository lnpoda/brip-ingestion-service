package com.poda.brip_ingestion_service.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawTransactionIngestionDomainEvent {

    // identity
    private String rawTransactionId;
    private String sourceSystem;
    private String sourceEventType;

    // core financial attributes
    private BigDecimal amount;
    private String currency;

    // account identifiers
    private String accountId;
    private String counterpartyAccountId;

    // timing
    private Instant eventTimestamp;
    private Instant ingestionTimestamp;

    // raw upstream payload (JSON string)
    private String rawPayload;

    // ingestion metadata
    private Integer partition;
    private Long offset;
    private String correlationId;
    private String eventId;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {


        private String rawTransactionId;
        private String sourceSystem;
        private String sourceEventType;

        // core financial attributes
        private BigDecimal amount;
        private String currency;

        // account identifiers
        private String accountId;
        private String counterpartyAccountId;

        // timing
        private Instant eventTimestamp;
        private Instant ingestionTimestamp;

        // raw upstream payload (JSON string)
        private String rawPayload;

        // ingestion metadata
        private Integer partition;
        private Long offset;
        private String correlationId;
        private String eventId;

        public Builder rawTransactionId(String rawTransactionId) {
            this.rawTransactionId = rawTransactionId;
            return this;
        }

        public Builder sourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public Builder sourceEventType(String sourceEventType) {
            this.sourceEventType = sourceEventType;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder counterpartyAccountId(String accountId) {
            this.counterpartyAccountId = accountId;
            return this;
        }

        public Builder eventTimestamp(Instant timestamp) {
            this.eventTimestamp = timestamp;
            return this;
        }

        public Builder ingestionTimestamp(Instant timestamp) {
            this.ingestionTimestamp = timestamp;
            return this;
        }

        public Builder rawPayload(String rawPayload) {
            this.rawPayload = rawPayload;
            return this;
        }


        public Builder partition(Integer partition) {
            this.partition = partition;
            return this;
        }

        public Builder offset(Long offset) {
            this.offset = offset;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public RawTransactionIngestionDomainEvent build() {
            return new RawTransactionIngestionDomainEvent(this.rawTransactionId,
                    this.sourceSystem,
                    this.sourceEventType,
                    this.amount,
                    this.currency,
                    this.accountId,
                    this.counterpartyAccountId,
                    this.eventTimestamp,
                    this.ingestionTimestamp,
                    this.rawPayload,
                    this.partition,
                    this.offset,
                    this.correlationId,
                    this.eventId
                    );
        }
    }
}
