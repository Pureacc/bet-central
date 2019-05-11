package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.CalculateNoVigOdds
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.DecimalOdds
import org.pureacc.betcentral.vocabulary.Percentage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.pureacc.betcentral.application.api.CalculateNoVigOdds.*

@SpringBootTest(classes = SpringAndReactApplication.class)
class CalculateNoVigOddsSpec extends Specification {
    @Autowired
    CalculateNoVigOdds calculateNoVigOdds

    @Unroll
    def "I can calculate the no-vig odds for a bet offer with odds #oddsA and #oddsB"() {
        given: "A bet with two sides"
        and: "Side A has odds of #oddsA"
        and: "Side B has odds of #oddsB"

        when: "I calculate the no-vig odds"
        Request request = Request.newBuilder().withOddsA(oddsA).withOddsB(oddsB).build()
        Response response = calculateNoVigOdds.execute(request)

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
}
