package com.thisisaniceteam.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ThisIsANiceBackendBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThisIsANiceBackendBatchApplication.class, args);
	}

}
