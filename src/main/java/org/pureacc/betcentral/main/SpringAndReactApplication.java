package org.pureacc.betcentral.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.pureacc.betcentral")
public class SpringAndReactApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);
	}
}
