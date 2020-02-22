package org.pureacc.betcentral.domain.model.snapshot;

import java.time.Instant;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.vocabulary.Euros;

public final class DepositSnapshot {
	private final User user;
	private final Euros euros;
	private final Instant date;

	private DepositSnapshot(Builder builder) {
		user = builder.user;
		euros = builder.euros;
		date = builder.date;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public User getUser() {
		return user;
	}

	public Euros getEuros() {
		return euros;
	}

	public Instant getDate() {
		return date;
	}

	public static final class Builder {
		private User user;
		private Euros euros;
		private Instant date;

		private Builder() {
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withEuros(Euros euros) {
			this.euros = euros;
			return this;
		}

		public Builder withDate(Instant date) {
			this.date = date;
			return this;
		}

		public DepositSnapshot build() {
			return new DepositSnapshot(this);
		}
	}
}
