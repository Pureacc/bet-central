package org.pureacc.betcentral.domain.events;

import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public final class BetPlacedEvent implements Event {
	private final UserId userId;
	private final DecimalOdds odds;
	private final Euros euros;

	private BetPlacedEvent(Builder builder) {
		userId = builder.userId;
		odds = builder.odds;
		euros = builder.euros;
	}

	public UserId getUserId() {
		return userId;
	}

	public DecimalOdds getOdds() {
		return odds;
	}

	public Euros getEuros() {
		return euros;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private UserId userId;
		private DecimalOdds odds;
		private Euros euros;

		private Builder() {
		}

		public Builder withUserId(UserId userId) {
			this.userId = userId;
			return this;
		}

		public Builder withOdds(DecimalOdds odds) {
			this.odds = odds;
			return this;
		}

		public Builder withEuros(Euros euros) {
			this.euros = euros;
			return this;
		}

		public BetPlacedEvent build() {
			return new BetPlacedEvent(this);
		}
	}
}
