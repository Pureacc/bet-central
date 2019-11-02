package org.pureacc.betcentral.infra.rest

import org.pureacc.betcentral.application.api.CreateDeposit
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.UserId
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.pureacc.betcentral.ResourceReader.asString
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = BalanceController.class)
@ContextConfiguration(classes = SpringAndReactApplication)
@Unroll
class BalanceControllerSpec extends Specification {
    @Autowired
    MockMvc mvc
    @SpringBean
    CreateDeposit createDeposit = Mock(CreateDeposit)
    @Value("classpath:web/balance-deposit-request.json")
    Resource depositRequest

    def "POST to #url with userId '#userId', euros '#euros' makes a deposit"() {
        when: "I make a deposit with userId '#userId', euros '#euros'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(depositRequest)))

        then: "A deposit is made"
        1 * createDeposit.execute({
            it.userId == UserId.of(userId)
            it.euros == Euros.of(euros)
        })
        and: "HTTP status is 200"
        result.andExpect(status().isOk())

        where:
        url                    | userId | euros
        "/api/balance/deposit" | 123    | 10
    }
}