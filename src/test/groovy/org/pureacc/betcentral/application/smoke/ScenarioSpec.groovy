package org.pureacc.betcentral.application.smoke

import org.pureacc.betcentral.application.api.*
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.DecimalOdds
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Username
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class)
class ScenarioSpec extends Specification {
    @Autowired
    CreateUser createUser
    @Autowired
    Authenticate authenticate
    @Autowired
    CreateDeposit createDeposit
    @Autowired
    PlaceBet placeBet
    @Autowired
    WinBet winBet
    @Autowired
    LoseBet loseBet
    @Autowired
    GetUser getUser

    def "I can create a user, deposit and place bets that win or lose"() {
        when: "I create a user"
        Username username = Username.of("John Doe")
        CreateUser.Request createUserRequest = CreateUser.Request.newBuilder()
                .withUsername(username).build()
        CreateUser.Response createUserResponse = createUser.execute(createUserRequest)
        and: "I authenticate"
        Authenticate.Request authenticateRequest = Authenticate.Request.newBuilder()
                .withUsername(username)
                .withPassword("dummy").build()
        authenticate.execute(authenticateRequest)
        and: "I deposit 50 euros"
        CreateDeposit.Request depositRequest = CreateDeposit.Request.newBuilder()
                .withUserId(createUserResponse.userId)
                .withEuros(Euros.of(50)).build()
        createDeposit.execute(depositRequest)
        and: "I place a bet for 10 euros with decimal odds of 3"
        PlaceBet.Request placeBetRequest = PlaceBet.Request.newBuilder()
                .withUserId(createUserResponse.userId)
                .withOdds(DecimalOdds.of(3))
                .withEuros(Euros.of(10)).build()
        PlaceBet.Response placeBetResponse = placeBet.execute(placeBetRequest)
        and: "The bet wins"
        WinBet.Request winBetRequest = WinBet.Request.newBuilder()
                .withBetId(placeBetResponse.betId).build()
        winBet.execute(winBetRequest)
        and: "I place a bet for 8 euros with decimal odds of 2.5"
        PlaceBet.Request placeBetRequest2 = PlaceBet.Request.newBuilder()
                .withUserId(createUserResponse.userId)
                .withOdds(DecimalOdds.of(2.5))
                .withEuros(Euros.of(8)).build()
        PlaceBet.Response placeBetResponse2 = placeBet.execute(placeBetRequest2)
        and: "The bet loses"
        LoseBet.Request loseBetRequest = LoseBet.Request.newBuilder()
                .withBetId(placeBetResponse2.betId).build()
        loseBet.execute(loseBetRequest)

        then: "My balance is 62 euros"
        GetUser.Request getUserRequest = GetUser.Request.newBuilder()
                .withUserId(createUserResponse.userId).build()
        GetUser.Response getUserResponse = getUser.execute(getUserRequest)
        getUserResponse.balance == Euros.of(62)
    }
}
