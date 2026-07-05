package com.poda.brip_ingestion_service.exception.domain.rules;

public class CrossFieldRuleViolationException extends DomainRuleViolationException {

    private static final String RULE = "CrossFieldRule";

    private CrossFieldRuleViolationException(String reason) {
        super(RULE, reason);
    }

    public static CrossFieldRuleViolationException missingAtmMetadata(String transactionId) {
        return new CrossFieldRuleViolationException(
                "transaction " + transactionId + " is an ATM withdrawal but lacks required ATM metadata"
        );
    }

    public static CrossFieldRuleViolationException missingTransferMetadata(String transactionId) {
        return new CrossFieldRuleViolationException(
                "transaction " + transactionId + " is a transfer but lacks required transfer metadata"
        );
    }
}