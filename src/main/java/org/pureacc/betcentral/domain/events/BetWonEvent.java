package org.pureacc.betcentral.domain.events;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public final class BetWonEvent implements Event {
	private final UserId userId;
	private final Euros amountWon;

	private BetWonEvent(Builder builder) {
		userId = builder.userId;
		amountWon = builder.amountWon;
	}

	public UserId getUserId() {
		return userId;
	}

	public Euros getAmountWon() {
		return amountWon;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private UserId userId;
		private Euros amountWon;

		private Builder() {
		}

		public Builder withUserId(UserId userId) {
			this.userId = userId;
			return this;
		}

		public Builder withAmountWon(Euros amountWon) {
			this.amountWon = amountWon;
			return this;
		}

		public BetWonEvent build() {
			return new BetWonEvent(this);
		}
	}
}
