package org.pureacc.betcentral.domain.events;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class WithdrawalEvent implements Event {
	private final UserId userId;
	private final Euros euros;

	@JsonCreator
	public WithdrawalEvent(@JsonProperty("userId") UserId userId, @JsonProperty("euros") Euros euros) {
		this.userId = userId;
		this.euros = euros;
	}

	public UserId getUserId() {
		return userId;
	}

	public Euros getEuros() {
		return euros;
	}
}
