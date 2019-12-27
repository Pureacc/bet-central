package infra.events.controller

import org.pureacc.betcentral.domain.events.BetWonEvent
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Operation
import org.pureacc.betcentral.vocabulary.UserId

class BetWonEventControllerSpec extends AbstractEventControllerSpec {
    private static final UserId USER_ID = UserId.of(1L)
    private static final Euros EUROS = Euros.of(50)

    def "A bet won event triggers an update of the user balance"() {
        when: "a bet won event is published"
        def event = BetWonEvent.newBuilder().withUserId(USER_ID).withAmountWon(EUROS).build()
        eventPublisher.publish(event)

        then: "an update of the user balance is triggered"
        1 * updateBalance.execute({ it.userId == USER_ID && it.euros == EUROS && it.operation == Operation.ADD })
    }
}
