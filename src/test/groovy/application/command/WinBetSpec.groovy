package application.command

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.WinBet
import org.pureacc.betcentral.domain.events.BetWonEvent
import org.pureacc.betcentral.domain.model.Bet
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.BetRepository
import org.pureacc.betcentral.vocabulary.BetId
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate
import static org.pureacc.betcentral.vocabulary.BetStatus.*

class WinBetSpec extends AbstractApplicationSpec {
    @Autowired
    WinBet winBet
    @Autowired
    BetRepository betRepository

    @Unroll
    def "An authenticated user can win a bet with status #status"() {
        given: "I am an authenticated user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        authenticate(user)
        and: "I have placed a bet of 5 euros with status #status"
        Bet bet = bets.aBet(user, Euros.of(5), status)
        testEventPublisher.clear()

        when: "I win the bet"
        WinBet.Request request = WinBet.Request.newBuilder()
                .withBetId(bet.id).build()
        winBet.execute(request)

        then: "The bet is won"
        Bet validateBet = betRepository.get(bet.id)
        with(validateBet) {
            validateBet.won
            validateBet.resolveDate == testTime.now()
        }
        and: "A BetWonEvent is published"
        BetWonEvent event = testEventPublisher.poll() as BetWonEvent
        with(event) {
            it.userId == user.id
            it.amountWon == Euros.of(10)
        }

        where:
        status  | _
        PENDING | _
    }

    @Unroll
    def "An authenticated user cannot win a bet with status #status"() {
        given: "I am an authenticated user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        authenticate(user)
        and: "I have placed a bet of 5 euros with status #status"
        Bet bet = bets.aBet(user, Euros.of(5), status)

        when: "I win the bet"
        WinBet.Request request = WinBet.Request.newBuilder()
                .withBetId(bet.id).build()
        winBet.execute(request)

        then: "An exception is thrown"
        UserException exception = thrown UserException
        exception.message == "The bet status is invalid"

        where:
        status | _
        WON    | _
        LOST   | _
    }

    @Unroll
    def "An authenticated user cannot win a bet with ID #betId"() {
        given: "I am an authenticated user"
        authenticate(users.aUser())

        when: "I win a bet with ID #id"
        WinBet.Request request = WinBet.Request.newBuilder()
                .withBetId(betId).build()
        winBet.execute(request)

        then: "An exception is thrown"
        thrown UserException

        where:
        betId        | _
        null         | _
        BetId.of(-1) | _
        BetId.of(0)  | _
    }

    def "An unauthenticated user's access is denied"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        winBet.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
