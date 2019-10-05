package org.pureacc.betcentral.main;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "org.pureacc.betcentral.infra.jpa.model")
@EnableJpaRepositories(basePackages = "org.pureacc.betcentral.infra.jpa.dao")
@Configuration
class JpaConfiguration {
}
