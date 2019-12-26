package org.pureacc.betcentral.application.command

import org.pureacc.betcentral.application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.UpdateBalance
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Operation
import org.pureacc.betcentral.vocabulary.exception.DomainException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static org.pureacc.betcentral.application.api.UpdateBalance.Request

class UpdateBalanceSpec extends AbstractApplicationSpec {
    @Autowired
    UpdateBalance updateBalance
    @Autowired
    UserRepository userRepository

    @Unroll
    def "The system can #operation #euros euros to a user's balance of #balance euros"() {
        given: "A user with a balance of #balance euros"
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
        given: "A user with a balance of #balance euros"
        User user = users.aUser(balance)

        when: "The system substracts #euros euros from the user's balance"
        Request request = Request.newBuilder()
                .withUserId(user.id)
                .withOperation(operation)
                .withEuros(euros).build()
        updateBalance.execute(request)

        then: "An exception is thrown"
        thrown DomainException

        where:
        balance      | operation           | euros
        Euros.of(0)  | Operation.SUBSTRACT       | Euros.of(5)
        Euros.of(10) | Operation.SUBSTRACT | Euros.of(15)
    }
}
