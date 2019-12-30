package application

import application.conf.ApplicationSpecConfiguration
import application.factory.Bets
import application.factory.Users
import application.mock.TestEventPublisher
import application.stub.TestTime
import infra.security.web.Authentications
import org.pureacc.betcentral.domain.service.DomainEventPublisher
import org.pureacc.betcentral.domain.service.DomainTime
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class)
@Import(ApplicationSpecConfiguration)
class AbstractApplicationSpec extends Specification {
    @Autowired
    protected Users users
    @Autowired
    protected Bets bets
    @Autowired
    protected TestEventPublisher testEventPublisher
    @Autowired
    protected TestTime testTime

    void setup() {
        DomainEventPublisher.setPublisher(testEventPublisher)
        DomainTime.setTime(testTime);
        testEventPublisher.clear()
    }

    void cleanup() {
        Authentications.unauthenticate()
    }
}
