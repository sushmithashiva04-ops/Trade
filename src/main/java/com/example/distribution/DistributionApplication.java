package com.example.distribution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class DistributionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributionApplication.class, args);
	}

}
