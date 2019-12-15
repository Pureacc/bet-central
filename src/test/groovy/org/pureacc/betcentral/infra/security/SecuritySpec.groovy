package org.pureacc.betcentral.infra.security

import org.pureacc.betcentral.application.ApplicationSpec
import org.pureacc.betcentral.infra.security.checks.IsAuthenticated
import org.pureacc.betcentral.vocabulary.exception.AccessDeniedException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import([TestCommandImpl])
class SecuritySpec extends ApplicationSpec {
    @Autowired
    TestCommand testCommand
    @SpringBean
    IsAuthenticated isAuthenticated = Stub()

    def "I cannot call a command method without allow annotations when I am not authenticated"() {
        given: "A command method without allow annotations"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        testCommand.allowNone()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I cannot call a command method without allow annotations when I am authenticated"() {
        given: "A command method without allow annotations"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        testCommand.allowNone()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can call a command method with allow-unauthenticated annotation when I am not authenticated"() {
        given: "A command method with allow-unauthenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        def response = testCommand.allowUnauthenticated()

        then: "The method returns successfully"
        response
    }

    def "I cannot call a command method with allow-unauthenticated annotation when I am authenticated"() {
        given: "A command method with allow-unauthenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = testCommand.allowUnauthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I cannot call a command method with allow-authenticated annotation when I am not authenticated"() {
        given: "A command method with allow-authenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        testCommand.allowAuthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can call a command method with allow-authenticated annotation when I am authenticated"() {
        given: "A command method with allow-authenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = testCommand.allowAuthenticated()

        then: "The method returns successfully"
        response
    }

    def "I can call a command method with allow-unauthenticated and allow-authenticated annotation when I am not authenticated"() {
        given: "A command method with allow-unauthenticated and allow-authenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        def response = testCommand.allowUnauthenticatedAndAuthenticated()

        then: "The method returns successfully"
        response
    }

    def "I can call a command method with allow-unauthenticated and allow-authenticated annotation when I am authenticated"() {
        given: "A command method with allow-unauthenticated and allow-authenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = testCommand.allowUnauthenticatedAndAuthenticated()

        then: "The method returns successfully"
        response
    }
}
