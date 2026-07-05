package com.poda.brip_ingestion_service.repository;

import com.poda.brip_ingestion_service.entity.RawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RawTransactionRepository extends JpaRepository<RawTransaction, Long> {

    public Optional<RawTransaction> findByRawTransactionId(String rawTransactionId);
}
