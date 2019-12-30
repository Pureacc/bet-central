package application.command

import application.AbstractApplicationSpec
import application.objectmother.UserObjectMother
import org.pureacc.betcentral.application.api.UpdateBalance
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.infra.security.web.AuthenticationService
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Operation
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static org.pureacc.betcentral.application.api.UpdateBalance.Request

class UpdateBalanceSpec extends AbstractApplicationSpec {
    @Autowired
    UpdateBalance updateBalance
    @Autowired
    UserRepository userRepository

    @Unroll
    def "The system can #operation #euros euros to a user's balance of #balance euros"() {
        given: "The system has the SYSTEM authority"
        User system = UserObjectMother.aUser()
        authenticate(system, AuthenticationService.Authority.SYSTEM)
        and: "A user with a balance of #balance euros"
        User user = users.aUser(balance)

        when: "The system #operation #euros euros to the user's balance"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOperation(operation)
                .withEuros(euros).build()
        updateBalance.execute(request)

        then: "The user's balance has been updated to #expectedBalance euros"
        User validateUser = userRepository.get(user.id)
        validateUser.balance.euros == expectedBalance

        where:
        balance      | operation           | euros        || expectedBalance
        Euros.of(50) | Operation.ADD       | Euros.of(10) || Euros.of(60)
        Euros.of(50) | Operation.SUBSTRACT | Euros.of(10) || Euros.of(40)
    }

    @Unroll
    def "The system cannot SUBSTRACT #euros euros from a user's balance of #balance euros"() {
        given: "The system has the SYSTEM authority"
        User system = UserObjectMother.aUser()
        authenticate(system, AuthenticationService.Authority.SYSTEM)
        and: "A user with a balance of #balance euros"
        User user = users.aUser(balance)

        when: "The system substracts #euros euros from the user's balance"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOperation(operation)
                .withEuros(euros).build()
        updateBalance.execute(request)

        then: "An exception is thrown"
        UserException exception = thrown UserException
        exception.message == "Your balance of ${balance} euros is insufficient"

        where:
        balance      | operation           | euros
        Euros.of(0)  | Operation.SUBSTRACT | Euros.of(5)
        Euros.of(10) | Operation.SUBSTRACT | Euros.of(15)
    }

    def "A user without the SYSTEM authority is denied access"() {
        given: "I am a user without the SYSTEM authority"
        User user = users.aUser()
        authenticate(user)

        when: "I call the use case"
        updateBalance.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
