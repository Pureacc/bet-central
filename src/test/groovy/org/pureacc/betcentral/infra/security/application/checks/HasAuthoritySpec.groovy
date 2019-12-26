package org.pureacc.betcentral.infra.security.application.checks

import org.pureacc.betcentral.infra.security.application.checks.HasAuthority.Authority
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Collections.emptyList
import static java.util.Collections.singletonList

class HasAuthoritySpec extends Specification {
    HasAuthority hasAuthority = new HasAuthority()

    @Unroll
    def "Check for #authority returns true when I have the #authority authority"(Authority authority) {
        given: "I have the #authority authority"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password",
                singletonList(new SimpleGrantedAuthority(authority.toString()))))

        expect: "Check for #authority returns true"
        hasAuthority.hasAuthority(authority)

        where:
        authority << Authority.values()
    }

    @Unroll
    def "Check for #authority returns false when I do not have the #authority authority"(Authority authority) {
        given: "I do not have the #authority authority"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password",
                emptyList()))

        expect: "Check for #authority returns false"
        !hasAuthority.hasAuthority(authority)

        where:
        authority << Authority.values()
    }
}
