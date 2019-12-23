package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.conf.ApplicationSpecConfiguration
import org.pureacc.betcentral.application.factory.Authentications
import org.pureacc.betcentral.application.factory.Bets
import org.pureacc.betcentral.application.factory.Users
import org.pureacc.betcentral.application.mock.TestEventPublisher
import org.pureacc.betcentral.application.stub.TestTime
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
        testEventPublisher.clear()
    }

    void cleanup() {
        Authentications.unauthenticate()
    }
}
