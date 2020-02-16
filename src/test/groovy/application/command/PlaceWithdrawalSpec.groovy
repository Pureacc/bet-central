package application.command

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.PlaceWithdrawal
import org.pureacc.betcentral.domain.events.WithdrawalEvent
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.model.Withdrawal
import org.pureacc.betcentral.domain.repository.WithdrawalRepository
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate
import static org.pureacc.betcentral.application.api.PlaceWithdrawal.Request

class PlaceWithdrawalSpec extends AbstractApplicationSpec {
    @Autowired
    PlaceWithdrawal withdraw
    @Autowired
    WithdrawalRepository withdrawalRepository

    @Unroll
    def "An authenticated user can withdraw #euros euros from his account with a balance of #balance euros"() {
        given: "I am an authenticated user with a balance of #balance euros"
        User user = users.aUser(balance)
        authenticate(user)

        when: "I withdraw #euros euros from my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        withdraw.execute(request)

        then: "A withdrawal is created"
        List<Withdrawal> withdrawals = withdrawalRepository.findByUser(user.id)
        with(withdrawals.get(0)) {
            it.user.id == user.id
            it.euros == euros
            it.date == testTime.now()
        }

        and: "A WithdrawalEvent is published"
        WithdrawalEvent event = testEventPublisher.poll() as WithdrawalEvent
        with(event) {
            it.userId == user.id
            it.euros == euros
        }

        where:
        euros            | balance
        Euros.of(50)     | Euros.of(50)
        Euros.of(100.65) | Euros.of(200)
    }

    @Unroll
    def "An authenticated user cannot withdraw #euros euros from his account with a balance of #balance euros"() {
        given: "I am an authenticated user with a balance of #balance euros"
        User user = users.aUser(balance)
        authenticate(user)

        when: "I withdraw #euros euros from my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        withdraw.execute(request)

        then: "An exception is thrown"
        def exception = thrown UserException
        exception.message == error

        where:
        euros        | balance      || error
        Euros.of(0)  | Euros.of(50) || "euros value must be greater than 0"
        Euros.of(-1) | Euros.of(50) || "euros value must be greater than 0"
        null         | Euros.of(50) || "euros must not be null"
        Euros.of(51) | Euros.of(50) || "Your balance of 50.0 euros is insufficient"
    }

    def "An authenticated user cannot withdraw euros from another user's account"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)
        and: "Another user"
        User anotherUser = users.aUser(Euros.of(20))

        when: "I withdraw euros from the other user's account"
        Request request = Request.newBuilder().withUserId(anotherUser.id).withEuros(Euros.of(10)).build()
        withdraw.execute(request)

        then: "Access is denied"
        thrown AccessDeniedException
    }

    def "An unauthenticated user's access is denied"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        withdraw.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
