package com.poda.brip_ingestion_service.config.domain.rules;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "domain-rules")
@Getter
@Setter
public class DomainRulesProperties {

    private TimestampRules timestamp = new TimestampRules();
    private IdempotencyRules idempotency = new IdempotencyRules();
    private CrossFieldRules crossField = new CrossFieldRules();
    private StateRules state = new StateRules();
    private BusinessUnitRules businessUnits = new BusinessUnitRules();
    private AmlRules aml = new AmlRules();
    private Toggles toggles = new Toggles();

    @Getter @Setter
    public static class TimestampRules {
        private int maxAgeDays;
        private int allowFutureSeconds;
    }

    @Getter @Setter
    public static class IdempotencyRules {
        private boolean enabled;
        private int lookbackHours;
    }

    @Getter @Setter
    public static class CrossFieldRules {
        private boolean requireAtmMetadataForWithdrawal;
        private boolean requireTransferMetadata;
    }

    @Getter @Setter
    public static class StateRules {
        private boolean rejectSettledReplays;
        private boolean rejectCancelledReplays;
    }

    @Getter @Setter
    public static class BusinessUnitRules {
        private AtmRules atm = new AtmRules();
        private CoreBankingRules corebanking = new CoreBankingRules();

        @Getter @Setter
        public static class AtmRules {
            private BigDecimal withdrawalLimit;
            private boolean requireLocation;
            private boolean requireTerminalId;
        }

        @Getter @Setter
        public static class CoreBankingRules {
            private BigDecimal maxTransferAmount;
            private boolean requireCustomerProfile;
        }
    }

    @Getter @Setter
    public static class AmlRules {
        private List<String> blacklistSources = new ArrayList<>();
        private BigDecimal maxHighRiskAmount;
        private List<String> highRiskCategories = new ArrayList<>();
    }

    @Getter @Setter
    public static class Toggles {
        private boolean enableCrossFieldRules;
        private boolean enableAmlRules;
        private boolean enableBusinessUnitRules;
    }
}