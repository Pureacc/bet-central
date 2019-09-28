package org.pureacc.betcentral.infra.events.controller;

import org.pureacc.betcentral.application.api.UpdateBalance;
import org.pureacc.betcentral.domain.events.BetPlacedEvent;
import org.pureacc.betcentral.vocabulary.Operation;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class BetPlacedEventController {
	private final UpdateBalance updateBalance;

	BetPlacedEventController(UpdateBalance updateBalance) {
		this.updateBalance = updateBalance;
	}

	@EventListener
	public void handle(BetPlacedEvent betPlacedEvent) {
		UpdateBalance.Request request = UpdateBalance.Request.newBuilder()
				.withUserId(betPlacedEvent.getUserId())
				.withOperation(Operation.SUBSTRACT)
				.withEuros(betPlacedEvent.getEuros())
				.build();
		updateBalance.execute(request);
	}
}
