package org.pureacc.betcentral.infra.events.controller

import org.pureacc.betcentral.application.api.UpdateBalance
import org.pureacc.betcentral.domain.service.EventPublisher
import org.pureacc.betcentral.infra.events.SpringEventPublisher
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [DepositEventController, BetPlacedEventController, BetWonEventController, SpringEventPublisher])
abstract class AbstractEventControllerSpec extends Specification {
    @Autowired
    protected EventPublisher eventPublisher
    @SpringBean
    protected UpdateBalance updateBalance = Mock()
}
