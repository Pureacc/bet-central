package org.pureacc.betcentral.infra.events.controller;

import org.pureacc.betcentral.application.api.UpdateBalance;
import org.pureacc.betcentral.domain.events.WithdrawalEvent;
import org.pureacc.betcentral.vocabulary.Operation;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class WithdrawalEventController {
	private final UpdateBalance updateBalance;

	WithdrawalEventController(UpdateBalance updateBalance) {
		this.updateBalance = updateBalance;
	}

	@EventListener
	public void handle(WithdrawalEvent withdrawalEvent) {
		UpdateBalance.Request request = UpdateBalance.Request.newBuilder()
				.withUserId(withdrawalEvent.getUserId())
				.withOperation(Operation.SUBSTRACT)
				.withEuros(withdrawalEvent.getEuros())
				.build();
		updateBalance.execute(request);
	}
}
