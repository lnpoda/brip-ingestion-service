package com.poda.brip_ingestion_service.service;

import com.poda.brip_ingestion_service.config.domain.rules.DomainRulesProperties;
import com.poda.brip_ingestion_service.exception.domain.rules.*;
import com.poda.brip_ingestion_service.model.RawTransaction;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainRulesService {

    private final DomainRulesProperties domainRulesProperties;

    private final Counter timestampRuleViolationCounter;
    private final Counter crossFieldRuleViolationCounter;
    private final Counter amlRuleViolationCounter;

    public DomainRulesService(DomainRulesProperties domainRulesProperties, MeterRegistry registry) {
        this.domainRulesProperties = domainRulesProperties;
        this.timestampRuleViolationCounter = registry.counter("domain.rules.timestamp.violations");
        this.crossFieldRuleViolationCounter = registry.counter("domain.rules.crossfield.violations");
        this.amlRuleViolationCounter = registry.counter("domain.rules.aml.violations");
    }

    public List<DomainRuleViolationException> applyIngestionRules(RawTransaction tx) {

        List<DomainRuleViolationException> violations = new ArrayList<>();

        applyTimestampRule(tx, violations);
        applyIdempotencyRule(tx, violations);
        applyCrossFieldRule(tx, violations);
        applyStateRule(tx, violations);
        applyBusinessUnitRules(tx, violations);
        applyAmlRules(tx, violations);

        return new ArrayList<>(violations);
    }

    // ------------------------------------------------------------
    // Timestamp Rule
    // ------------------------------------------------------------
    private void applyTimestampRule(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var ts = domainRulesProperties.getTimestamp();

        long ageDays = calculateAgeInDays(tx.getEventTimestamp());
        if (ageDays > ts.getMaxAgeDays()) {
            violations.add(TimestampRuleViolationException.tooOld((int) ageDays, ts.getMaxAgeDays()));
            timestampRuleViolationCounter.increment();
        }

        long futureSeconds = Duration.between(Instant.now(), tx.getEventTimestamp()).getSeconds();
        if (futureSeconds > ts.getAllowFutureSeconds()) {
            violations.add(TimestampRuleViolationException.tooFuture(futureSeconds, ts.getAllowFutureSeconds()));
            timestampRuleViolationCounter.increment();
        }
    }

    // ------------------------------------------------------------
    // Idempotency Rule (placeholder)
    // ------------------------------------------------------------
    private void applyIdempotencyRule(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var id = domainRulesProperties.getIdempotency();

        if (!id.isEnabled()) {
            return;
        }

        // TODO: Replace with real duplicate lookup
        boolean isDuplicate = false;

        if (isDuplicate) {
            violations.add(IdempotencyRuleViolationException.duplicateFound(
                    tx.getRawTransactionId(), id.getLookbackHours()
            ));
        }
    }

    // ------------------------------------------------------------
    // Cross-Field Rule
    // ------------------------------------------------------------
    private void applyCrossFieldRule(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var cf = domainRulesProperties.getCrossField();

        if (cf.isRequireAtmMetadataForWithdrawal()
                && isAtmWithdrawal(tx)
                && !rawPayloadHasAtmMetadata(tx)) {
            violations.add(CrossFieldRuleViolationException.missingAtmMetadata(tx.getRawTransactionId()));
            crossFieldRuleViolationCounter.increment();
        }

        if (cf.isRequireTransferMetadata()
                && isTransfer(tx)
                && !rawPayloadHasTransferMetadata(tx)) {
            violations.add(CrossFieldRuleViolationException.missingTransferMetadata(tx.getRawTransactionId()));
            crossFieldRuleViolationCounter.increment();
        }
    }

    // ------------------------------------------------------------
    // State Rule (placeholder)
    // ------------------------------------------------------------
    private void applyStateRule(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var state = domainRulesProperties.getState();

        if (state.isRejectSettledReplays() && isSettledReplay(tx)) {
            violations.add(StateRuleViolationException.settledReplay(tx.getRawTransactionId()));
        }

        if (state.isRejectCancelledReplays() && isCancelledReplay(tx)) {
            violations.add(StateRuleViolationException.cancelledReplay(tx.getRawTransactionId()));
        }
    }

    // ------------------------------------------------------------
    // Business Unit Rules
    // ------------------------------------------------------------
    private void applyBusinessUnitRules(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var bu = domainRulesProperties.getBusinessUnits();

        if (isAtm(tx)) {
            var atm = bu.getAtm();

            if (tx.getAmount().compareTo(atm.getWithdrawalLimit()) > 0) {
                violations.add(BusinessUnitRuleViolationException.atmWithdrawalLimitExceeded(
                        tx.getRawTransactionId(), tx.getAmount(), atm.getWithdrawalLimit()
                ));
            }

            if (atm.isRequireLocation() && !rawPayloadHasAtmLocation(tx)) {
                violations.add(BusinessUnitRuleViolationException.missingAtmLocation(tx.getRawTransactionId()));
            }

            if (atm.isRequireTerminalId() && !rawPayloadHasTerminalId(tx)) {
                violations.add(BusinessUnitRuleViolationException.missingTerminalId(tx.getRawTransactionId()));
            }
        }

        if (isCoreBanking(tx)) {
            var cb = bu.getCorebanking();

            if (tx.getAmount().compareTo(cb.getMaxTransferAmount()) > 0) {
                violations.add(BusinessUnitRuleViolationException.coreBankingTransferLimitExceeded(
                        tx.getRawTransactionId(), tx.getAmount(), cb.getMaxTransferAmount()
                ));
            }

            if (cb.isRequireCustomerProfile() && !rawPayloadHasCustomerProfile(tx)) {
                violations.add(BusinessUnitRuleViolationException.missingCustomerProfile(tx.getRawTransactionId()));
            }
        }
    }

    // ------------------------------------------------------------
    // AML Rule
    // ------------------------------------------------------------
    private void applyAmlRules(RawTransaction tx, List<DomainRuleViolationException> violations) {
        var aml = domainRulesProperties.getAml();

        if (aml.getBlacklistSources().contains(tx.getSourceSystem())) {
            violations.add(AmlRuleViolationException.blacklistedSource(
                    tx.getRawTransactionId(), tx.getSourceSystem()
            ));
            amlRuleViolationCounter.increment();
        }

        if (aml.getHighRiskCategories().contains(tx.getSourceEventType())
                && tx.getAmount().compareTo(aml.getMaxHighRiskAmount()) > 0) {
            violations.add(AmlRuleViolationException.highRiskCategoryExceeded(
                    tx.getRawTransactionId(),
                    tx.getSourceEventType(),
                    tx.getAmount(),
                    aml.getMaxHighRiskAmount()
            ));
            amlRuleViolationCounter.increment();
        }
    }

    // ------------------------------------------------------------
    // Helper Methods
    // ------------------------------------------------------------
    private long calculateAgeInDays(Instant timestamp) {
        return Duration.between(timestamp, Instant.now()).toDays();
    }

    private Instant nowPlusSeconds(long seconds) {
        return Instant.now().plusSeconds(seconds);
    }

    private boolean isAtm(RawTransaction tx) {
        return "ATM".equalsIgnoreCase(tx.getSourceSystem());
    }

    private boolean isCoreBanking(RawTransaction tx) {
        return "COREBANKING".equalsIgnoreCase(tx.getSourceSystem());
    }

    private boolean isAtmWithdrawal(RawTransaction tx) {
        return isAtm(tx) && "WITHDRAWAL".equalsIgnoreCase(tx.getSourceEventType());
    }

    private boolean isTransfer(RawTransaction tx) {
        return "TRANSFER".equalsIgnoreCase(tx.getSourceEventType());
    }

    private boolean isSettledReplay(RawTransaction tx) {
        return false;
    }

    private boolean isCancelledReplay(RawTransaction tx) {
        return false;
    }

    private boolean rawPayloadHasAtmMetadata(RawTransaction tx) {
        return tx.getRawPayload() != null;
    }

    private boolean rawPayloadHasTransferMetadata(RawTransaction tx) {
        return tx.getRawPayload() != null;
    }

    private boolean rawPayloadHasAtmLocation(RawTransaction tx) {
        return tx.getRawPayload() != null;
    }

    private boolean rawPayloadHasTerminalId(RawTransaction tx) {
        return tx.getRawPayload() != null;
    }

    private boolean rawPayloadHasCustomerProfile(RawTransaction tx) {
        return tx.getRawPayload() != null;
    }
}