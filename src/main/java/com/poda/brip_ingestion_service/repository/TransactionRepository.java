package com.poda.brip_ingestion_service.repository;

import com.poda.brip_ingestion_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReferenceId(String transactionReferenceId);
}
