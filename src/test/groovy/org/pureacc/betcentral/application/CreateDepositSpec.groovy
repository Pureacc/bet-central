package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.CreateDeposit
import org.pureacc.betcentral.domain.events.DepositEvent
import org.pureacc.betcentral.domain.model.Deposit
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.DepositRepository
import org.pureacc.betcentral.vocabulary.Euros
import org.springframework.beans.factory.annotation.Autowired

import static org.pureacc.betcentral.application.api.CreateDeposit.Request

class CreateDepositSpec extends ApplicationSpec {
    @Autowired
    CreateDeposit deposit
    @Autowired
    DepositRepository depositRepository

    def "A user can deposit money into his account"() {
        given: "I am a user"
        User user = users.aUser()

        when: "I deposit 50 euros into my account"
        def euros = new Euros(50)
        Request request = Request.newBuilder().withUserId(user.userId).withEuros(euros).build()
        deposit.execute(request)

        then: "A deposit is created"
        List<Deposit> deposits = depositRepository.findByUser(user.userId)
        with(deposits.get(0)) {
            it.user.userId == user.userId
            it.euros == euros
        }

        and: "A DepositEvent is published"
        DepositEvent event = testEventPublisher.poll() as DepositEvent
        with(event) {
            it.userId == user.userId
            it.euros == euros
        }
    }
}
