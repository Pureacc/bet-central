package org.pureacc.betcentral.domain.model.snapshot;

import java.util.Date;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.BetStatus;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;

public final class BetSnapshot {
	private final BetId betId;
	private final User user;
	private final DecimalOdds odds;
	private final Euros euros;
	private final Date placedDate;
	private final Date resolveDate;
	private final BetStatus status;

	private BetSnapshot(Builder builder) {
		betId = builder.betId;
		user = builder.user;
		odds = builder.odds;
		euros = builder.euros;
		placedDate = builder.placedDate;
		resolveDate = builder.resolveDate;
		status = builder.status;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public BetId getBetId() {
		return betId;
	}

	public User getUser() {
		return user;
	}

	public DecimalOdds getOdds() {
		return odds;
	}

	public Euros getEuros() {
		return euros;
	}

	public Date getPlacedDate() {
		return placedDate;
	}

	public Date getResolveDate() {
		return resolveDate;
	}

	public BetStatus getStatus() {
		return status;
	}

	public static final class Builder {
		private BetId betId;
		private User user;
		private DecimalOdds odds;
		private Euros euros;
		private Date placedDate;
		private Date resolveDate;
		private BetStatus status;

		private Builder() {
		}

		public Builder withBetId(BetId betId) {
			this.betId = betId;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
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

		public Builder withPlacedDate(Date placedDate) {
			this.placedDate = placedDate;
			return this;
		}

		public Builder withResolveDate(Date resolveDate) {
			this.resolveDate = resolveDate;
			return this;
		}

		public Builder withStatus(BetStatus status) {
			this.status = status;
			return this;
		}

		public BetSnapshot build() {
			return new BetSnapshot(this);
		}
	}
}
