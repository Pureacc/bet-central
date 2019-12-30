package application.command

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.CreateDeposit
import org.pureacc.betcentral.domain.events.DepositEvent
import org.pureacc.betcentral.domain.model.Deposit
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.DepositRepository
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate
import static org.pureacc.betcentral.application.api.CreateDeposit.Request

class CreateDepositSpec extends AbstractApplicationSpec {
    @Autowired
    CreateDeposit deposit
    @Autowired
    DepositRepository depositRepository

    @Unroll
    def "An authenticated user can deposit #euros euros into his account"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I deposit #euros euros into my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        deposit.execute(request)

        then: "A deposit is created"
        List<Deposit> deposits = depositRepository.findByUser(user.id)
        with(deposits.get(0)) {
            it.user.id == user.id
            it.euros == euros
            it.date == testTime.now()
        }

        and: "A DepositEvent is published"
        DepositEvent event = testEventPublisher.poll() as DepositEvent
        with(event) {
            it.userId == user.id
            it.euros == euros
        }

        where:
        euros            | _
        Euros.of(50)     | _
        Euros.of(100.65) | _
    }

    @Unroll
    def "An authenticated user cannot deposit #euros euros into his account"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I deposit #euros euros into my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        deposit.execute(request)

        then: "An exception is thrown"
        thrown UserException

        where:
        euros        | _
        Euros.of(0)  | _
        Euros.of(-1) | _
        null         | _
    }

    def "An unauthenticated user's access is denied"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        deposit.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
