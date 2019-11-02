package org.pureacc.betcentral.application.api;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;

public interface Authenticate {
	Response execute(Request request);

	final class Request {
		private final Username username;
		private final String password;

		private Request(Builder builder) {
			username = builder.username;
			password = builder.password;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Username getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public static final class Builder {
			private Username username;
			private String password;

			private Builder() {
			}

			public Builder withUsername(Username username) {
				this.username = username;
				return this;
			}

			public Builder withPassword(String password) {
				this.password = password;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}

	final class Response {
		private final UserId userId;
		private final Username username;
		private final Euros balance;

		private Response(Builder builder) {
			userId = builder.userId;
			username = builder.username;
			balance = builder.balance;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public UserId getUserId() {
			return userId;
		}

		public Username getUsername() {
			return username;
		}

		public Euros getBalance() {
			return balance;
		}

		public static final class Builder {
			private UserId userId;
			private Username username;
			private Euros balance;

			private Builder() {
			}

			public Builder withUserId(UserId userId) {
				this.userId = userId;
				return this;
			}

			public Builder withUsername(Username username) {
				this.username = username;
				return this;
			}

			public Builder withBalance(Euros balance) {
				this.balance = balance;
				return this;
			}

			public Response build() {
				return new Response(this);
			}
		}
	}
}
