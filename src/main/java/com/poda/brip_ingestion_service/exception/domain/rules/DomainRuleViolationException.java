package com.poda.brip_ingestion_service.exception.domain.rules;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DomainRuleViolationException extends RuntimeException {


    public DomainRuleViolationException(String ruleName, String reason)
    {
        super(ruleName+" : "+reason);
    }
}
