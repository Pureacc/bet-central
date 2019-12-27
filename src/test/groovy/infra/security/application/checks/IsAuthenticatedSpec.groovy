package infra.security.application.checks

import org.pureacc.betcentral.infra.security.application.checks.IsAuthenticated
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

import static java.util.Collections.emptyList

class IsAuthenticatedSpec extends Specification {
    IsAuthenticated isAuthenticated = new IsAuthenticated()

    def "Returns true when I am authenticated"() {
        given: "I am authenticated"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password", emptyList()))

        expect:
        isAuthenticated.authenticated
    }

    def "Returns false when I am not authenticated"() {
        given: "I am not authenticated"
        SecurityContextHolder.clearContext()

        expect:
        !isAuthenticated.authenticated
    }
}
