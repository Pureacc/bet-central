package org.pureacc.betcentral.infra.events.controller

import org.pureacc.betcentral.domain.service.EventPublisher
import org.pureacc.betcentral.infra.events.SpringEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@EnableAspectJAutoProxy
@ContextConfiguration(classes = [EventSecurityAspect, TestEventController, SpringEventPublisher])
class EventSecurityAspectSpec extends Specification {
    @Autowired
    EventPublisher eventPublisher

    def "An event listener method is run as a system user"() {
        when: "an event is published"
        def event = new TestEventController.TestEvent()
        eventPublisher.publish(event)

        then: "the event is handled as a system user"
        event.handled
        and: "the authentication is reset after handling"
        SecurityContextHolder.getContext()
                .getAuthentication() == null
    }
}