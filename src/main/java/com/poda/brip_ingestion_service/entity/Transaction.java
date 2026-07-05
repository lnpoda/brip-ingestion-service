package com.poda.brip_ingestion_service.entity;


import com.poda.brip_ingestion_service.constants.Channel;
import com.poda.brip_ingestion_service.constants.TransactionStatus;
import com.poda.brip_ingestion_service.constants.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

// NOTE: This is to be moved to normalization service
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long transactionId;

    @Column(unique = true, nullable = false, length = 100)
    private String transactionReferenceId;

    @Column
    private Instant transactionStartTime;

    @Column
    private Instant transactionEndTime;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    private Instant amountDeductionTime;

    @Column
    private Instant amountAdditionTime;

    @ManyToOne
    @JoinColumn(name = "sender_account_id", nullable = false)
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private Account receiverAccount;

    @Column(length = 3, nullable = false)
    private String transactionCurrencyCode;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(precision = 19, scale = 4)
    private BigDecimal fees;

    @Column(precision = 19, scale = 4)
    private BigDecimal netAmount;

    @Column
    @Enumerated(EnumType.STRING)
    private Channel channel;

    @Column
    @CreationTimestamp
    private Instant createdAt;

    @Column
    @UpdateTimestamp
    private Instant updatedAt;

    @Column
    private String  createdBy;

    @Column
    private String updatedBy;

    @Column(length = 64)
    private String payloadHash;

}
