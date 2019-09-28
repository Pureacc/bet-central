package org.pureacc.betcentral.infra.events.controller;

import org.pureacc.betcentral.application.api.UpdateBalance;
import org.pureacc.betcentral.domain.events.BetWonEvent;
import org.pureacc.betcentral.vocabulary.Operation;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class BetWonEventController {
	private final UpdateBalance updateBalance;

	BetWonEventController(UpdateBalance updateBalance) {
		this.updateBalance = updateBalance;
	}

	@EventListener
	public void handle(BetWonEvent betWonEvent) {
		UpdateBalance.Request request = UpdateBalance.Request.newBuilder()
				.withUserId(betWonEvent.getUserId())
				.withOperation(Operation.ADD)
				.withEuros(betWonEvent.getAmountWon())
				.build();
		updateBalance.execute(request);
	}
}
