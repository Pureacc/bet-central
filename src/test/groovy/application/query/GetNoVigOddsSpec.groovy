package application.query

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.GetNoVigOdds
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.vocabulary.DecimalOdds
import org.pureacc.betcentral.vocabulary.Percentage
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.pureacc.betcentral.application.api.GetNoVigOdds.Request
import static org.pureacc.betcentral.application.api.GetNoVigOdds.Response
import static application.factory.Authentications.authenticate
import static application.factory.Authentications.unauthenticate

class GetNoVigOddsSpec extends AbstractApplicationSpec {
    @Autowired
    GetNoVigOdds getNoVigOdds

    @Unroll
    def "I can get the no-vig odds for a bet offer with odds #oddsA and #oddsB"() {
        given: "A bet with two sides"
        and: "Side A has odds of #oddsA"
        and: "Side B has odds of #oddsB"

        when: "I get the no-vig odds"
        Request request = Request.newBuilder().withOddsA(oddsA).withOddsB(oddsB).build()
        Response response = getNoVigOdds.execute(request)

        then: "The vig charged for the bet is #vig"
        response.vig == vig
        and: "Side A has no-vig odds of #noVigA"
        response.noVigOddsA == noVigA
        and: "Side B has no-vig odds of #noVigB"
        response.noVigOddsB == noVigB

        where:
        oddsA                | oddsB                || vig                              | noVigA                             | noVigB
        DecimalOdds.of(1.76) | DecimalOdds.of(2.18) || Percentage.of(2.689741451209329) | DecimalOdds.of(1.8073394495412842) | DecimalOdds.of(2.2386363636363633)
        DecimalOdds.of(8)    | DecimalOdds.of(1.07) || Percentage.of(5.957943925233636) | DecimalOdds.of(8.47663551401869)   | DecimalOdds.of(1.13375)
        DecimalOdds.of(2)    | DecimalOdds.of(2)    || Percentage.of(0)                 | DecimalOdds.of(2)                  | DecimalOdds.of(2)
    }

    @Unroll
    def "I cannot get the no-vig odds for a bet offer with odds #oddsA and #oddsB"() {
        given: "A bet with two sides"
        and: "Side A has odds of #oddsA"
        and: "Side B has odds of #oddsB"

        when: "I get the no-vig odds"
        Request request = Request.newBuilder().withOddsA(oddsA).withOddsB(oddsB).build()
        getNoVigOdds.execute(request)

        then: "An exception is thrown"
        thrown ConstraintViolationException

        where:
        oddsA                | oddsB
        DecimalOdds.of(0.90) | DecimalOdds.of(2.18)
    }

    def "An authenticated user has access"() {
        given: "I am authenticated"
        User user = users.aUser()
        authenticate(user)

        when: "I call the use case"
        Request request = Request.newBuilder().withOddsA(DecimalOdds.of(2)).withOddsB(DecimalOdds.of(2)).build()
        Response response = getNoVigOdds.execute(request)

        then: "I receive a successful response"
        response
    }

    def "An unauthenticated user has access"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        Request request = Request.newBuilder().withOddsA(DecimalOdds.of(2)).withOddsB(DecimalOdds.of(2)).build()
        Response response = getNoVigOdds.execute(request)

        then: "I receive a successful response"
        response
    }
}
