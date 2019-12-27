package infra.events.controller


import org.pureacc.betcentral.domain.events.BetPlacedEvent
import org.pureacc.betcentral.vocabulary.DecimalOdds
import org.pureacc.betcentral.vocabulary.Euros
import org.pureacc.betcentral.vocabulary.Operation
import org.pureacc.betcentral.vocabulary.UserId

class BetPlacedEventControllerSpec extends AbstractEventControllerSpec {
    private static final UserId USER_ID = UserId.of(1L)
    private static final Euros EUROS = Euros.of(50)
    private static final DecimalOdds ODDS = DecimalOdds.of(2.0)

    def "A bet placed event triggers an update of the user balance"() {
        when: "a bet placed event is published"
        def event = BetPlacedEvent.newBuilder().withUserId(USER_ID).withEuros(EUROS).withOdds(ODDS).build()
        eventPublisher.publish(event)

        then: "an update of the user balance is triggered"
        1 * updateBalance.execute({ it.userId == USER_ID && it.euros == EUROS && it.operation == Operation.SUBSTRACT })
    }
}
