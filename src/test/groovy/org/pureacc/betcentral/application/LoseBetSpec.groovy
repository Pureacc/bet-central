package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.LoseBet
import org.pureacc.betcentral.domain.model.Bet
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.BetRepository
import org.pureacc.betcentral.vocabulary.BetId
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.exception.DomainException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.pureacc.betcentral.vocabulary.BetStatus.*

class LoseBetSpec extends ApplicationSpec {
    @Autowired
    LoseBet loseBet
    @Autowired
    BetRepository betRepository

    @Unroll
    def "A user can lose a bet with status #status"() {
        given: "I am a user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        and: "I have placed a bet of 5 euros with status #status"
        Bet bet = bets.aBet(user, Euros.of(5), status)

        when: "I lose the bet"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(bet.id).build()
        loseBet.execute(request)

        then: "The bet is lost"
        Bet validateBet = betRepository.get(bet.id)
        with(validateBet) {
            validateBet.lost
            validateBet.resolveDate == testTime.now()
        }

        where:
        status  | _
        PENDING | _
    }

    @Unroll
    def "A user cannot lose a bet with status #status"() {
        given: "I am a user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        and: "I have placed a bet of 5 euros with status #status"
        Bet bet = bets.aBet(user, Euros.of(5), status)

        when: "I lose the bet"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(bet.id).build()
        loseBet.execute(request)

        then: "An exception is thrown"
        thrown DomainException

        where:
        status | _
        WON    | _
        LOST   | _
    }

    @Unroll
    def "A user cannot lose a bet with ID #betId"() {
        when: "I lose a bet with ID #id"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(betId).build()
        loseBet.execute(request)

        then: "An exception is thrown"
        thrown ConstraintViolationException

        where:
        betId        | _
        null         | _
        BetId.of(-1) | _
        BetId.of(0)  | _
    }
}
