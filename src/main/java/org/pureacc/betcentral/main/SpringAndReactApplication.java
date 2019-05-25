package org.pureacc.betcentral.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "org.pureacc.betcentral.infra.jpa.model")
@EnableJpaRepositories(basePackages = "org.pureacc.betcentral.infra.jpa.dao")
@SpringBootApplication(scanBasePackages = "org.pureacc.betcentral")
public class SpringAndReactApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAndReactApplication.class, args);
	}
}
