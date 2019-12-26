package org.pureacc.betcentral.infra.events.controller


import org.pureacc.betcentral.domain.events.DepositEvent
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Operation
import org.pureacc.betcentral.vocabulary.UserId

class DepositEventControllerSpec extends AbstractEventControllerSpec {
    private static final UserId USER_ID = UserId.of(1L)
    private static final Euros EUROS = Euros.of(50)

    def "A deposit event triggers an update of the user balance"() {
        when: "a deposit event is published"
        eventPublisher.publish(new DepositEvent(USER_ID, EUROS))

        then: "an update of the user balance is triggered"
        1 * updateBalance.execute({ it.userId == USER_ID && it.euros == EUROS && it.operation == Operation.ADD })
    }
}
