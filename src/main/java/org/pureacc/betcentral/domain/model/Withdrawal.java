package org.pureacc.betcentral.domain.model;

import static org.pureacc.betcentral.domain.service.DomainTime.now;

import java.time.Instant;

import org.pureacc.betcentral.domain.events.WithdrawalEvent;
import org.pureacc.betcentral.domain.model.snapshot.WithdrawalSnapshot;
import org.pureacc.betcentral.domain.service.DomainEventPublisher;
import org.pureacc.betcentral.vocabulary.Euros;

public class Withdrawal {
	private User user;
	private Euros euros;
	private Instant date;

	public Withdrawal(User user, Euros euros) {
		user.validateSufficientBalance(euros);
		this.user = user;
		this.euros = euros;
		this.date = now();
		WithdrawalEvent event = new WithdrawalEvent(user.getId(), euros);
		DomainEventPublisher.publish(event);
	}

	public Withdrawal(WithdrawalSnapshot withdrawalSnapshot) {
		this.user = withdrawalSnapshot.getUser();
		this.euros = withdrawalSnapshot.getEuros();
		this.date = withdrawalSnapshot.getDate();
	}

	public WithdrawalSnapshot toSnapshot() {
		return WithdrawalSnapshot.newBuilder()
				.withUser(user)
				.withEuros(euros)
				.withDate(date)
				.build();
	}
}
