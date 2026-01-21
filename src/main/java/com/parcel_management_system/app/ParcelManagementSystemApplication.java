package com.parcel_management_system.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ParcelManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelManagementSystemApplication.class, args);
	}

}
