package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.pureacc.betcentral.vocabulary.annotation.SecuredResource;

public interface LoseBet {
	@Allow(AUTHENTICATED)
	void execute(@Valid Request request);

	final class Request {
		@SecuredResource
		@Valid
		@NotNull
		private final BetId betId;

		private Request(Builder builder) {
			betId = builder.betId;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public BetId getBetId() {
			return betId;
		}

		public static final class Builder {
			private BetId betId;

			private Builder() {
			}

			public Builder withBetId(BetId betId) {
				this.betId = betId;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}
}
