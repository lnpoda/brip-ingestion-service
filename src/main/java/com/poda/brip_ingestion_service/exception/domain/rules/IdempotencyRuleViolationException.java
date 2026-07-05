package com.poda.brip_ingestion_service.exception.domain.rules;

public class IdempotencyRuleViolationException extends DomainRuleViolationException {

    private static final String RULE = "IdempotencyRule";

    private IdempotencyRuleViolationException(String reason) {
        super(RULE, reason);
    }

    public static IdempotencyRuleViolationException duplicateFound(String transactionId, int lookbackHours) {
        return new IdempotencyRuleViolationException(
                "transaction " + transactionId + " is a duplicate within the last " + lookbackHours + " hours"
        );
    }

    public static IdempotencyRuleViolationException lookupFailed(String transactionId) {
        return new IdempotencyRuleViolationException(
                "duplicate lookup failed for transaction " + transactionId
        );
    }
}