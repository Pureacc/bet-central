package org.pureacc.betcentral.infra.security

import org.pureacc.betcentral.application.ApplicationSpec
import org.pureacc.betcentral.infra.security.checks.IsAuthenticated
import org.pureacc.betcentral.vocabulary.exception.AccessDeniedException
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import([TestAuthenticationSecuredClass, TestUnsecuredClass])
class SecuritySpec extends ApplicationSpec {
    @Autowired
    TestAuthenticationSecuredClass authenticationSecuredClass
    @Autowired
    TestUnsecuredClass unsecuredClass
    @SpringBean
    IsAuthenticated isAuthenticated = Stub()

    def "I can call an unsecured method in an authentication-secured class when I am authenticated"() {
        given: "An unsecured method in an authentication-secured class"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = authenticationSecuredClass.method()

        then: "The method returns successfully"
        response
    }

    def "I can call an authentication-secured method in an authentication-secured class when I am authenticated"() {
        given: "An authentication-secured method in an authentication-secured class"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = authenticationSecuredClass.authenticationSecuredMethod()

        then: "The method returns successfully"
        response
    }

    def "I can call an authentication-secured method in an unsecured class when I am authenticated"() {
        given: "An authentication-secured method in an unsecured class"
        and: "I am authenticated"
        isAuthenticated.isAuthenticated() >> true

        when: "I call the method"
        def response = unsecuredClass.authenticationSecuredMethod()

        then: "The method returns successfully"
        response
    }

    def "I can call an authentication-dropped-secured method in an authentication-secured class when I am not authenticated"() {
        given: "An authentication-dropped-secured method in an authentication-secured class"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        def response = authenticationSecuredClass.authenticationDroppedSecuredMethod()

        then: "The method returns successfully"
        response
    }

    def "I can't call an unsecured method in an authentication-secured class when I am not authenticated"() {
        given: "An unsecured method in an authentication-secured class"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        authenticationSecuredClass.method()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can't call an authentication-secured method in an authentication-secured class when I am not authenticated"() {
        given: "An authentication-secured method in an authentication-secured class"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        authenticationSecuredClass.authenticationSecuredMethod()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can't call an authentication-secured method in an unsecured class when I am not authenticated"() {
        given: "An authentication-secured method in an unsecured class"
        and: "I am not authenticated"
        isAuthenticated.isAuthenticated() >> false

        when: "I call the method"
        unsecuredClass.authenticationSecuredMethod()

        then: "The method throws access denied"
        thrown AccessDeniedException
    }
}
