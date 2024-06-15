package com.madmin.policies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.madmin.policies")
//@ComponentScan(basePackages = "com.madmin.policies")
public class PoliciesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliciesApplication.class, args);
	}

}
