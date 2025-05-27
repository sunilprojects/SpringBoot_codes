package com.ats;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class AtsHiringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtsHiringSystemApplication.class, args);
		System.out.println("hello aroha");
		log.info("output is storing in log");
	}
}
