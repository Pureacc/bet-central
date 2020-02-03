package application.smoke


import org.pureacc.betcentral.application.api.*
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.transaction.Transactional

import static infra.security.web.Authentications.authenticate

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class)
class ScenarioSpec extends Specification {
    @Autowired
    UserRepository userRepository
    @Autowired
    CreateUser createUser
    @Autowired
    PlaceDeposit placeDeposit
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
        CreateUser.Request createUserRequest = CreateUser.Request.newBuilder()
                .withUsername(Username.of("John Doe"))
                .withPassword(Password.of("hunter2")).build()
        CreateUser.Response createUserResponse = createUser.execute(createUserRequest)
        and: "I am authenticated as that user"
        UserId userId = createUserResponse.userId
        User user = userRepository.get(userId)
        authenticate(user)
        and: "I deposit 50 euros"
        PlaceDeposit.Request depositRequest = PlaceDeposit.Request.newBuilder()
                .withUserId(userId)
                .withEuros(Euros.of(50)).build()
        placeDeposit.execute(depositRequest)
        and: "I place a bet for 10 euros with decimal odds of 3"
        PlaceBet.Request placeBetRequest = PlaceBet.Request.newBuilder()
                .withUserId(userId)
                .withOdds(DecimalOdds.of(3))
                .withEuros(Euros.of(10)).build()
        PlaceBet.Response placeBetResponse = placeBet.execute(placeBetRequest)
        and: "The bet wins"
        WinBet.Request winBetRequest = WinBet.Request.newBuilder()
                .withBetId(placeBetResponse.betId).build()
        winBet.execute(winBetRequest)
        and: "I place a bet for 8 euros with decimal odds of 2.5"
        PlaceBet.Request placeBetRequest2 = PlaceBet.Request.newBuilder()
                .withUserId(userId)
                .withOdds(DecimalOdds.of(2.5))
                .withEuros(Euros.of(8)).build()
        PlaceBet.Response placeBetResponse2 = placeBet.execute(placeBetRequest2)
        and: "The bet loses"
        LoseBet.Request loseBetRequest = LoseBet.Request.newBuilder()
                .withBetId(placeBetResponse2.betId).build()
        loseBet.execute(loseBetRequest)

        then: "My balance is 62 euros"
        GetUser.Request getUserRequest = GetUser.Request.newBuilder()
                .withUserId(userId).build()
        GetUser.Response getUserResponse = getUser.execute(getUserRequest)
        getUserResponse.balance == Euros.of(62)
    }
}
