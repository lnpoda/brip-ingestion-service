package com.poda.brip_ingestion_service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest(properties = {
		"spring.kafka.listener.auto-startup=false",
		"spring.kafka.bootstrap-servers=localhost:12345", // dummy
		"spring.sql.init.mode=never",
		"spring.cloud.aws.secretsmanager.enabled=false",
		"spring.cloud.aws.parameterstore.enabled=false",
		"upstream.atm.topic=test-atm",
		"upstream.corebanking.topic=test-corebanking"


})
class BripIngestionServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
