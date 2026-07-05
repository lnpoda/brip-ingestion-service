package com.poda.brip_ingestion_service;

import com.poda.brip_ingestion_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BripIngestionServiceApplication {
	@Autowired
	TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(BripIngestionServiceApplication.class, args);
	}

}
