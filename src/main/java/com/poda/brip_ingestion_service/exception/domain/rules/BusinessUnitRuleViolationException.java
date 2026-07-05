package com.poda.brip_ingestion_service.exception.domain.rules;

public class BusinessUnitRuleViolationException extends DomainRuleViolationException {

    private static final String RULE = "BusinessUnitRule";

    private BusinessUnitRuleViolationException(String reason) {
        super(RULE, reason);
    }

    // ATM
    public static BusinessUnitRuleViolationException atmWithdrawalLimitExceeded(
            String transactionId, Number amount, Number limit) {
        return new BusinessUnitRuleViolationException(
                "ATM transaction " + transactionId + " amount " + amount +
                        " exceeds withdrawal limit " + limit
        );
    }

    public static BusinessUnitRuleViolationException missingAtmLocation(String transactionId) {
        return new BusinessUnitRuleViolationException(
                "ATM transaction " + transactionId + " is missing required ATM location metadata"
        );
    }

    public static BusinessUnitRuleViolationException missingTerminalId(String transactionId) {
        return new BusinessUnitRuleViolationException(
                "ATM transaction " + transactionId + " is missing required terminal ID metadata"
        );
    }

    // CoreBanking
    public static BusinessUnitRuleViolationException coreBankingTransferLimitExceeded(
            String transactionId, Number amount, Number limit) {
        return new BusinessUnitRuleViolationException(
                "CoreBanking transaction " + transactionId + " amount " + amount +
                        " exceeds max transfer amount " + limit
        );
    }

    public static BusinessUnitRuleViolationException missingCustomerProfile(String transactionId) {
        return new BusinessUnitRuleViolationException(
                "CoreBanking transaction " + transactionId + " is missing required customer profile metadata"
        );
    }

    // Mobile
    public static BusinessUnitRuleViolationException mobileTransferLimitExceeded(
            String transactionId, Number amount, Number limit) {
        return new BusinessUnitRuleViolationException(
                "Mobile transaction " + transactionId + " amount " + amount +
                        " exceeds max mobile transfer amount " + limit
        );
    }

    public static BusinessUnitRuleViolationException missingDeviceId(String transactionId) {
        return new BusinessUnitRuleViolationException(
                "Mobile transaction " + transactionId + " is missing required device ID metadata"
        );
    }
}