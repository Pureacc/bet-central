package org.pureacc.betcentral.domain.model;

import static org.pureacc.betcentral.domain.service.DomainTime.now;

import java.time.Instant;

import org.pureacc.betcentral.domain.events.DepositEvent;
import org.pureacc.betcentral.domain.model.snapshot.DepositSnapshot;
import org.pureacc.betcentral.domain.service.DomainEventPublisher;
import org.pureacc.betcentral.vocabulary.Euros;

public class Deposit {
	private User user;
	private Euros euros;
	private Instant date;

	public Deposit(User user, Euros euros) {
		this.user = user;
		this.euros = euros;
		this.date = now();
		DepositEvent event = new DepositEvent(user.getId(), euros);
		DomainEventPublisher.publish(event);
	}

	public Deposit(DepositSnapshot depositSnapshot) {
		this.user = depositSnapshot.getUser();
		this.euros = depositSnapshot.getEuros();
		this.date = depositSnapshot.getDate();
	}

	public DepositSnapshot toSnapshot() {
		return DepositSnapshot.newBuilder()
				.withUser(user)
				.withEuros(euros)
				.withDate(date)
				.build();
	}
}
