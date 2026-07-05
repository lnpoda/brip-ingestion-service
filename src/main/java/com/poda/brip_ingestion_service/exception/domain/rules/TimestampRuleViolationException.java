package com.poda.brip_ingestion_service.exception.domain.rules;

public class TimestampRuleViolationException extends DomainRuleViolationException {

    private static String RULE = "TimestampRule";


    public TimestampRuleViolationException(String reason) {
        super(RULE, reason);
    }

    public static TimestampRuleViolationException tooOld(int actualDays, int maxAgeDays) {
        return new TimestampRuleViolationException("the transaction is "+actualDays+
                " days old while the allowed is "+maxAgeDays+" days");
    }

    public static TimestampRuleViolationException tooFuture(long actualFutureSeconds, long allowedFutureSeconds) {
        return new TimestampRuleViolationException("the transaction is "+actualFutureSeconds+
                " seconds in the future while the allowed is "+allowedFutureSeconds+" seconds");
    }
}
