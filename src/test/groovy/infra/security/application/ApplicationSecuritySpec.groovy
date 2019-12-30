package infra.security.application


import application.AbstractApplicationSpec
import org.pureacc.betcentral.infra.security.application.checks.HasAuthority
import org.pureacc.betcentral.infra.security.application.checks.IsAuthenticated
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import spock.lang.Unroll

import static org.pureacc.betcentral.infra.security.application.checks.HasAuthority.*

@Import([TestCommandImpl, TestQueryImpl])
class ApplicationSecuritySpec extends AbstractApplicationSpec {
    @Autowired
    TestCommand command
    @Autowired
    TestQuery query
    @SpringBean
    IsAuthenticated isAuthenticated = Stub()
    @SpringBean
    HasAuthority hasAuthority = Stub()

    @Unroll
    def "I cannot call a command method without allow annotations when my authentication is #authenticated"() {
        given: "A command method without allow annotations"
        and: "My authentication is #authenticated"
        isAuthenticated.isAuthenticated() >> authenticated

        when: "I call the method"
        command.allowNone()

        then: "The method throws access denied"
        thrown AccessDeniedException

        where:
        authenticated | _
        true          | _
        false         | _
    }

    @Unroll
    def "I can call a command method with allow-unauthenticated and allow-authenticated annotation when my authentication is #authenticated"() {
        given: "A command method with allow-unauthenticated and allow-authenticated annotation"
        and: "My authentication is #authenticated"
        isAuthenticated.isAuthenticated() >> authenticated

        when: "I call the method"
        boolean response = command.allowUnauthenticatedAndAuthenticated()

        then: "The method returns successfully"
        response

        where:
        authenticated | _
        true          | _
        false         | _
    }

    def "I can call a command method with allow-unauthenticated annotation when I am not authenticated"() {
        given: "A command method with allow-unauthenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        boolean response = command.allowUnauthenticated()

        then: "The method returns successfully"
        response
    }

    def "I cannot call a command method with allow-unauthenticated annotation when I am authenticated"() {
        given: "A command method with allow-unauthenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        command.allowUnauthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I cannot call a command method with allow-authenticated annotation when I am not authenticated"() {
        given: "A command method with allow-authenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        command.allowAuthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can call a command method with allow-authenticated annotation when I am authenticated"() {
        given: "A command method with allow-authenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        boolean response = command.allowAuthenticated()

        then: "The method returns successfully"
        response
    }

    def "I can call a command method with allow-system annotation when I have the system role"() {
        given: "A command method with allow-system annotation"
        and: "I have the system role"
        hasAuthority.hasAuthority(Authority.SYSTEM) >> true

        when: "I call the method"
        boolean response = command.allowSystem()

        then: "The method returns successfully"
        response
    }

    def "I cannot call a command method with allow-system annotation when I do not have the system role"() {
        given: "A command method with allow-system annotation"
        and: "I do not have the system role"
        hasAuthority.hasAuthority(Authority.SYSTEM) >> false

        when: "I call the method"
        command.allowSystem()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    @Unroll
    def "I cannot call a query method without allow annotations when my authentication is #authenticated"() {
        given: "A query method without allow annotations"
        and: "My authentication is #authenticated"
        isAuthenticated.isAuthenticated() >> authenticated

        when: "I call the method"
        query.allowNone()

        then: "The method throws access denied"
        thrown AccessDeniedException

        where:
        authenticated | _
        true          | _
        false         | _
    }

    @Unroll
    def "I can call a query method with allow-unauthenticated and allow-authenticated annotation when my authentication is #authenticated"() {
        given: "A query method with allow-unauthenticated and allow-authenticated annotation"
        and: "My authentication is #authenticated"
        isAuthenticated.isAuthenticated() >> authenticated

        when: "I call the method"
        boolean response = query.allowUnauthenticatedAndAuthenticated()

        then: "The method returns successfully"
        response

        where:
        authenticated | _
        true          | _
        false         | _
    }

    def "I can call a query method with allow-unauthenticated annotation when I am not authenticated"() {
        given: "A query method with allow-unauthenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        boolean response = query.allowUnauthenticated()

        then: "The method returns successfully"
        response
    }

    def "I cannot call a query method with allow-unauthenticated annotation when I am authenticated"() {
        given: "A query method with allow-unauthenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        query.allowUnauthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I cannot call a query method with allow-authenticated annotation when I am not authenticated"() {
        given: "A query method with allow-authenticated annotation"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        query.allowAuthenticated()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can call a query method with allow-authenticated annotation when I am authenticated"() {
        given: "A query method with allow-authenticated annotation"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        boolean response = query.allowAuthenticated()

        then: "The method returns successfully"
        response
    }

    def "I can call a query method with allow-system annotation when I have the system role"() {
        given: "A query method with allow-system annotation"
        and: "I have the system role"
        hasAuthority.hasAuthority(Authority.SYSTEM) >> true

        when: "I call the method"
        boolean response = query.allowSystem()

        then: "The method returns successfully"
        response
    }

    def "I cannot call a query method with allow-system annotation when I do not have the system role"() {
        given: "A query method with allow-system annotation"
        and: "I do not have the system role"
        hasAuthority.hasAuthority(Authority.SYSTEM) >> false

        when: "I call the method"
        query.allowSystem()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }
}
