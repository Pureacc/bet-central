package infra.rest

import org.pureacc.betcentral.application.api.LoseBet
import org.pureacc.betcentral.application.api.PlaceBet
import org.pureacc.betcentral.application.api.WinBet
import org.pureacc.betcentral.infra.rest.BetController
import org.pureacc.betcentral.vocabulary.BetId
import org.pureacc.betcentral.vocabulary.DecimalOdds
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

@WebMvcTest(controllers = BetController)
@Unroll
class BetControllerSpec extends AbstractControllerSpec {
    @SpringBean
    PlaceBet placeBet = Mock(PlaceBet)
    @SpringBean
    WinBet winBet = Mock(WinBet)
    @SpringBean
    LoseBet loseBet = Mock(LoseBet)
    @Value("classpath:web/bet-place-request.json")
    Resource placeBetRequest
    @Value("classpath:web/bet-win-request.json")
    Resource winBetRequest
    @Value("classpath:web/bet-lose-request.json")
    Resource loseBetRequest

    def "POST to #url with userId '#userId', odds '#odds', euros '#euros' places a bet and returns the bet id"() {
        when: "I place a bet with userId '#userId', odds '#odds', euros '#euros'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(placeBetRequest)))

        then: "A bet is placed"
        1 * placeBet.execute({
            it.userId == UserId.of(userId)
            it.odds == DecimalOdds.of(odds)
            it.euros == Euros.of(euros)
        }) >> PlaceBet.Response.newBuilder().withBetId(BetId.of(betId)).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the bet id"
        result.andExpect(jsonPath('$.betId').value(betId))

        where:
        url              | userId | odds | euros || betId
        "/api/bet/place" | 123    | 2.0  | 10    || 456
    }

    def "POST to #url with betId '#betId' wins a bet"() {
        when: "I place a bet with betId '#betId'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(winBetRequest)))

        then: "A bet is won"
        1 * winBet.execute({
            it.betId == BetId.of(betId)
        })
        and: "HTTP status is 200"
        result.andExpect(status().isOk())

        where:
        url            | betId
        "/api/bet/win" | 123
    }

    def "POST to #url with betId '#betId' loses a bet"() {
        when: "I place a bet with betId '#betId'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(loseBetRequest)))

        then: "A bet is lost"
        1 * loseBet.execute({
            it.betId == BetId.of(betId)
        })
        and: "HTTP status is 200"
        result.andExpect(status().isOk())

        where:
        url             | betId
        "/api/bet/lose" | 123
    }
}