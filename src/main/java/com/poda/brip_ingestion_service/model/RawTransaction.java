package com.poda.brip_ingestion_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RawTransaction {

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

    // rawEvent payload
    private Object rawPayload;

    private RawTransaction(Builder builder) {
        this.sourceSystem = builder.sourceSystem;
        this.sourceEventType = builder.sourceEventType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.accountId = builder.accountId;
        this.counterpartyAccountId = builder.counterpartyAccountId;
        this.eventTimestamp = builder.eventTimestamp;
        this.ingestionTimestamp = builder.ingestionTimestamp;
        this.rawPayload = builder.rawPayload;
    }


    public static class Builder {

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

        // rawEvent payload
        private Object rawPayload;

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

        public Builder eventTimestamp(Instant eventTimestamp) {
            this.eventTimestamp = eventTimestamp;
            return this;
        }

        public Builder ingestionTimestamp(Instant ingestionTimestamp) {
            this.ingestionTimestamp = ingestionTimestamp;
            return this;
        }

        public Builder rawPayload(Object rawPayload) {
            this.rawPayload = rawPayload;
            return this;
        }

        public RawTransaction build() {
            return new RawTransaction(this);
        }
    }
}
