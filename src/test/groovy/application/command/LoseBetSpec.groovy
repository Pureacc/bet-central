package application.command

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.LoseBet
import org.pureacc.betcentral.domain.model.Bet
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.BetRepository
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.vocabulary.BetId
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate
import static org.pureacc.betcentral.vocabulary.BetStatus.*

class LoseBetSpec extends AbstractApplicationSpec {
    @Autowired
    LoseBet loseBet
    @Autowired
    BetRepository betRepository

    @Unroll
    def "An authenticated user can lose a bet with status #status"() {
        given: "I am an authenticated user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        authenticate(user)
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
    def "An authenticated user cannot lose a bet with status #status"() {
        given: "I am an authenticated user with a balance of 50 euros"
        User user = users.aUser(Euros.of(50))
        authenticate(user)
        and: "I have placed a bet of 5 euros with status #status"
        Bet bet = bets.aBet(user, Euros.of(5), status)

        when: "I lose the bet"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(bet.id).build()
        loseBet.execute(request)

        then: "An exception is thrown"
        UserException exception = thrown UserException
        exception.message == "The bet status is invalid"

        where:
        status | _
        WON    | _
        LOST   | _
    }

    @Unroll
    def "An authenticated user cannot lose a bet with ID #betId"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I lose a bet with ID #id"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(betId).build()
        loseBet.execute(request)

        then: "An exception is thrown"
        thrown UserException

        where:
        betId        | _
        null         | _
        BetId.of(-1) | _
        BetId.of(0)  | _
    }

    def "An authenticated user cannot lose another user's bet"() {
        given: "I am an authenticated user"
        authenticate(users.aUser())
        and: "Another user with a balance of 50 euros"
        User anotherUser = users.aUser(Euros.of(50))
        and: "The other user has placed a bet of 5 euros with status PENDING"
        Bet bet = bets.aBet(anotherUser, Euros.of(5), PENDING)

        when: "I lose the other user's bet"
        LoseBet.Request request = LoseBet.Request.newBuilder()
                .withBetId(bet.id).build()
        loseBet.execute(request)

        then: "Access is denied"
        thrown AccessDeniedException
    }

    def "An unauthenticated user's access is denied"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        loseBet.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
