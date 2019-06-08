package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.CreateDeposit
import org.pureacc.betcentral.domain.events.DepositEvent
import org.pureacc.betcentral.domain.model.Deposit
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.DepositRepository
import org.pureacc.betcentral.vocabulary.Euros
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.pureacc.betcentral.application.api.CreateDeposit.Request

class CreateDepositSpec extends ApplicationSpec {
    @Autowired
    CreateDeposit deposit
    @Autowired
    DepositRepository depositRepository

    @Unroll
    def "A user can deposit #euros euros into his account"() {
        given: "I am a user"
        User user = users.aUser()

        when: "I deposit #euros euros into my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        deposit.execute(request)

        then: "A deposit is created"
        List<Deposit> deposits = depositRepository.findByUser(user.id)
        with(deposits.get(0)) {
            it.user.id == user.id
            it.euros == euros
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
    def "A user cannot deposit #euros euros into his account"() {
        given: "I am a user"
        User user = users.aUser()

        when: "I deposit #euros euros into my account"
        Request request = Request.newBuilder().withUserId(user.id).withEuros(euros).build()
        deposit.execute(request)

        then: "An exception is thrown"
        thrown ConstraintViolationException

        where:
        euros        | _
        Euros.of(0)  | _
        Euros.of(-1) | _
        null         | _
    }
}
