package com.zoho.ats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class ZohoAtsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZohoAtsSystemApplication.class, args);
		System.out.println("hello aroha From Zoho ATS");
		log.info("output is storing in log");
	}

}
