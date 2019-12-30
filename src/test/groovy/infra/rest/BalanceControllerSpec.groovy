package infra.rest

import org.pureacc.betcentral.application.api.CreateDeposit
import org.pureacc.betcentral.infra.rest.BalanceController
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.UserId
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.Resource
import spock.lang.Unroll

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static testutil.ResourceReader.asString

@WebMvcTest(controllers = BalanceController.class)
@Unroll
class BalanceControllerSpec extends AbstractControllerSpec {
    @SpringBean
    CreateDeposit createDeposit = Mock(CreateDeposit)
    @Value("classpath:web/balance-deposit-request.json")
    Resource depositRequest

    def "POST to #url with userId '#userId', euros '#euros' makes a deposit and returns the balance '#balance'"() {
        when: "I make a deposit with userId '#userId', euros '#euros'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(depositRequest)))

        then: "A deposit is made"
        1 * createDeposit.execute({
            it.userId == UserId.of(userId)
            it.euros == Euros.of(euros)
        }) >> CreateDeposit.Response.newBuilder().withBalance(Euros.of(balance)).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the balance"
        result.andExpect(jsonPath('$.balance').value(balance))

        where:
        url                    | userId | euros || balance
        "/api/balance/deposit" | 123    | 10    || 50
    }
}