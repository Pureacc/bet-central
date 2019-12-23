package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.PlaceBet
import org.pureacc.betcentral.domain.events.BetPlacedEvent
import org.pureacc.betcentral.domain.model.Bet
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.BetRepository
import org.pureacc.betcentral.vocabulary.DecimalOdds
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.exception.DomainException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.pureacc.betcentral.application.api.PlaceBet.Request
import static org.pureacc.betcentral.application.api.PlaceBet.Response
import static org.pureacc.betcentral.application.factory.Authentications.authenticate

class PlaceBetSpec extends AbstractApplicationSpec {
    @Autowired
    PlaceBet placeBet
    @Autowired
    BetRepository betRepository

    @Unroll
    def "An authenticated user with a balance of #balance euros can place a bet with odds #odds for #euros euros"() {
        given: "I am an authenticated user with a balance of #balance euros"
        User user = users.aUser(balance)
        authenticate(user)

        when: "I place a bet with odds #odds for #euros euros"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOdds(odds)
                .withEuros(euros).build()
        Response response = placeBet.execute(request)

        then: "A bet is placed"
        Bet bet = betRepository.get(response.getBetId())
        with(bet) {
            bet.id == response.getBetId()
            bet.user.id == user.id
            bet.odds == odds
            bet.stake == euros
            bet.placedDate == testTime.now()
        }
        and: "A BetPlacedEvent is published"
        BetPlacedEvent event = testEventPublisher.poll() as BetPlacedEvent
        with(event) {
            it.userId == user.id
            it.odds == odds
            it.euros == euros
        }

        where:
        balance      | odds                 | euros
        Euros.of(50) | DecimalOdds.of(1.75) | Euros.of(5)
        Euros.of(50) | DecimalOdds.of(3)    | Euros.of(50)
    }

    @Unroll
    def "An authenticated user with a balance of #balance euros does not have sufficient funds to place a bet for #euros euros"() {
        given: "I am an authenticated user with a balance of #balance euros"
        User user = users.aUser(balance)
        authenticate(user)

        when: "I place a bet for #euros euros"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOdds(DecimalOdds.of(2))
                .withEuros(euros).build()
        placeBet.execute(request)

        then: "An exception is thrown"
        thrown DomainException

        where:
        balance        | euros
        Euros.of(50)   | Euros.of(100)
        Euros.of(50.5) | Euros.of(51)
    }

    @Unroll
    def "An authenticated user cannot place a bet with odds #odds for #euros euro"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I place a bet with odds #odds for #euros euros"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOdds(odds)
                .withEuros(euros).build()
        placeBet.execute(request)

        then: "An exception is thrown"
        thrown ConstraintViolationException

        where:
        odds                 | euros
        DecimalOdds.of(0.90) | Euros.of(5)
        DecimalOdds.of(-2)   | Euros.of(5)
        DecimalOdds.of(2)    | Euros.of(-1)
        DecimalOdds.of(2)    | Euros.of(0)
    }
}
