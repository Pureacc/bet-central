package org.pureacc.betcentral.application.conf;

import org.pureacc.betcentral.application.factory.Users;
import org.pureacc.betcentral.application.mock.TestEventPublisher;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.domain.service.DomainEventPublisher;
import org.pureacc.betcentral.domain.service.EventPublisher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class ApplicationSpecConfiguration {
    @Bean
    Users users(UserRepository userRepository) {
        return new Users(userRepository);
    }

    @Primary
    @Bean
    EventPublisher testEventPublisher() {
        TestEventPublisher testEventPublisher = new TestEventPublisher();
        DomainEventPublisher.setPublisher(testEventPublisher);
        return testEventPublisher;
    }
}
