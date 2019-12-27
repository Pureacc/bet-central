package org.pureacc.betcentral.domain.model;

import static org.pureacc.betcentral.domain.service.DomainTime.now;
import static org.pureacc.betcentral.vocabulary.BetStatus.LOST;
import static org.pureacc.betcentral.vocabulary.BetStatus.PENDING;
import static org.pureacc.betcentral.vocabulary.BetStatus.WON;

import java.util.Date;

import org.pureacc.betcentral.domain.events.BetPlacedEvent;
import org.pureacc.betcentral.domain.events.BetWonEvent;
import org.pureacc.betcentral.domain.events.Event;
import org.pureacc.betcentral.domain.model.snapshot.BetSnapshot;
import org.pureacc.betcentral.domain.service.DomainEventPublisher;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.BetStatus;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.exception.DomainException;

public class Bet {
	private BetId betId;
	private User user;
	private DecimalOdds odds;
	private Euros stake;
	private Date placedDate;
	private Date resolveDate;
	private BetStatus status;

	public Bet(User user, DecimalOdds odds, Euros stake) {
		user.validateSufficientBalance(stake);
		this.user = user;
		this.odds = odds;
		this.stake = stake;
		this.placedDate = now();
		this.status = PENDING;
		publishBetPlacedEvent();
	}

	public Bet(BetSnapshot betSnapshot) {
		this.betId = betSnapshot.getBetId();
		this.user = betSnapshot.getUser();
		this.odds = betSnapshot.getOdds();
		this.stake = betSnapshot.getEuros();
		this.placedDate = betSnapshot.getPlacedDate();
		this.resolveDate = betSnapshot.getResolveDate();
		this.status = betSnapshot.getStatus();
	}

	public BetSnapshot toSnapshot() {
		return BetSnapshot.newBuilder()
				.withBetId(betId)
				.withUser(user)
				.withOdds(odds)
				.withEuros(stake)
				.withPlacedDate(placedDate)
				.withResolveDate(resolveDate)
				.withStatus(status)
				.build();
	}

	public BetId getId() {
		return betId;
	}

	public boolean isWon() {
		return status == WON;
	}

	public boolean isLost() {
		return status == LOST;
	}

	public void win() {
		validatePending();
		resolveDate = now();
		status = WON;
		publishBetWonEvent();
	}

	public void lose() {
		validatePending();
		resolveDate = now();
		status = LOST;
	}

	private Euros getPotentialPayout() {
		return odds.calculate(stake);
	}

	private void validatePending() {
		if (status != PENDING) {
			throw new DomainException("bet.status.invalid");
		}
	}

	private void publishBetPlacedEvent() {
		Event event = BetPlacedEvent.newBuilder()
				.withUserId(user.getId())
				.withOdds(odds)
				.withEuros(stake)
				.build();
        DomainEventPublisher.publish(event);
	}

	private void publishBetWonEvent() {
		Event event = BetWonEvent.newBuilder()
				.withUserId(user.getId())
				.withAmountWon(getPotentialPayout())
				.build();
		DomainEventPublisher.publish(event);
	}
}
