package com.thisisaniceteam.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThisIsANiceBackendBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThisIsANiceBackendBatchApplication.class, args);
	}

}
