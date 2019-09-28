package org.pureacc.betcentral.infra.events.controller;

import org.pureacc.betcentral.application.api.UpdateBalance;
import org.pureacc.betcentral.domain.events.DepositEvent;
import org.pureacc.betcentral.vocabulary.Operation;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class DepositEventController {
	private final UpdateBalance updateBalance;

	DepositEventController(UpdateBalance updateBalance) {
		this.updateBalance = updateBalance;
	}

	@EventListener
	public void handle(DepositEvent depositEvent) {
		UpdateBalance.Request request = UpdateBalance.Request.newBuilder()
				.withUserId(depositEvent.getUserId())
				.withOperation(Operation.ADD)
				.withEuros(depositEvent.getEuros())
				.build();
		updateBalance.execute(request);
	}
}
