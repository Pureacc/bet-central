package org.pureacc.betcentral.infra.rest

import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.infra.security.web.CryptoService
import org.pureacc.betcentral.infra.security.web.UserDetailsImpl
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.UserId
import org.pureacc.betcentral.vocabulary.Username
import org.spockframework.spring.StubBeans
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.filter.GenericFilterBean
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@StubBeans([UserRepository, CryptoService, AuthenticationSuccessHandler, AuthenticationFailureHandler])
@ContextConfiguration(classes = [SpringAndReactApplication, ControllerSpecConfiguration])
class AbstractControllerSpec extends Specification {
    @Autowired
    protected MockMvc mvc

    static class ControllerSpecConfiguration {
        @Primary
        @Bean
        GenericFilterBean successfullyAuthenticatedFilter() {
            new GenericFilterBean() {
                @Override
                void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                    UserSnapshot userSnapshot = UserSnapshot.newBuilder().withUserId(UserId.of(1L)).withUsername(Username.of("John Doe")).build()
                    UserDetails userDetails = new UserDetailsImpl(new User(userSnapshot))
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities()))
                    filterChain.doFilter(servletRequest, servletResponse)
                }
            }
        }
    }
}
