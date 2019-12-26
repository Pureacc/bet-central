package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.SYSTEM;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.Operation;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.annotation.Allow;

public interface UpdateBalance {
	@Allow({ SYSTEM })
	void execute(@Valid Request request);

	final class Request {
		@Valid
		@NotNull
		private final UserId userId;
		@Valid
		@NotNull
		private final Euros euros;
		@NotNull
		private final Operation operation;

		private Request(Builder builder) {
			userId = builder.userId;
			euros = builder.euros;
			operation = builder.operation;
		}

		public UserId getUserId() {
			return userId;
		}

		public Euros getEuros() {
			return euros;
		}

		public Operation getOperation() {
			return operation;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private @Valid @NotNull UserId userId;
			private @Valid @NotNull Euros euros;
			private @NotNull Operation operation;

			private Builder() {
			}

			public Builder withUserId(@Valid @NotNull UserId userId) {
				this.userId = userId;
				return this;
			}

			public Builder withEuros(@Valid @NotNull Euros euros) {
				this.euros = euros;
				return this;
			}

			public Builder withOperation(@NotNull Operation operation) {
				this.operation = operation;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}
}
