package com.poda.brip_ingestion_service.exception.domain.rules;

public class StateRuleViolationException extends DomainRuleViolationException {

    private static final String RULE = "StateRule";

    private StateRuleViolationException(String reason) {
        super(RULE, reason);
    }

    public static StateRuleViolationException settledReplay(String transactionId) {
        return new StateRuleViolationException(
                "transaction " + transactionId + " is a replay of a settled transaction, which is not allowed"
        );
    }

    public static StateRuleViolationException cancelledReplay(String transactionId) {
        return new StateRuleViolationException(
                "transaction " + transactionId + " is a replay of a cancelled transaction, which is not allowed"
        );
    }
}