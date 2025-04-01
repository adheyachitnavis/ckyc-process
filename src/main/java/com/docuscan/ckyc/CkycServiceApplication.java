package com.docuscan.ckyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CkycServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CkycServiceApplication.class, args);
	}

}
