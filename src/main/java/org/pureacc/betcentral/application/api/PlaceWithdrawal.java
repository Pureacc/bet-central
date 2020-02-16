package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.pureacc.betcentral.vocabulary.annotation.SecuredResource;

public interface PlaceWithdrawal {
	@Allow(AUTHENTICATED)
	void execute(@Valid Request request);

	final class Request {
		@SecuredResource
		@Valid
		@NotNull
		private final UserId userId;
		@Valid
		@NotNull
		private final Euros euros;

		private Request(Builder builder) {
			userId = builder.userId;
			euros = builder.euros;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public UserId getUserId() {
			return userId;
		}

		public Euros getEuros() {
			return euros;
		}

		public static final class Builder {
			private UserId userId;
			private Euros euros;

			private Builder() {
			}

			public Builder withUserId(UserId userId) {
				this.userId = userId;
				return this;
			}

			public Builder withEuros(Euros euros) {
				this.euros = euros;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}
}
