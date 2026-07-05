package com.poda.brip_ingestion_service.exception.domain.rules;

public class AmlRuleViolationException extends DomainRuleViolationException {

    private static final String RULE = "AmlRule";

    private AmlRuleViolationException(String reason) {
        super(RULE, reason);
    }

    public static AmlRuleViolationException blacklistedSource(String transactionId, String sourceSystem) {
        return new AmlRuleViolationException(
                "transaction " + transactionId + " originates from blacklisted source system: " + sourceSystem
        );
    }

    public static AmlRuleViolationException highRiskCategoryExceeded(
            String transactionId, String category, Number amount, Number limit) {
        return new AmlRuleViolationException(
                "transaction " + transactionId + " in high-risk category " + category +
                        " has amount " + amount + " exceeding limit " + limit
        );
    }
}