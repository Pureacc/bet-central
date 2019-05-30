package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.conf.ApplicationSpecConfiguration
import org.pureacc.betcentral.application.factory.Users
import org.pureacc.betcentral.application.mock.TestEventPublisher
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class)
@Import(ApplicationSpecConfiguration)
class ApplicationSpec extends Specification {
    @Autowired
    protected Users users
    @Autowired
    protected TestEventPublisher testEventPublisher
}
