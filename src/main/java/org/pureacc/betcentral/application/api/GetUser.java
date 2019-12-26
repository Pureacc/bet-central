package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;
import org.pureacc.betcentral.vocabulary.annotation.Allow;

public interface GetUser {
    @Allow(AUTHENTICATED)
    Response execute(Request request);

    final class Request {
        @Valid
        @NotNull
        private final UserId userId;

        private Request(Builder builder) {
            userId = builder.userId;
        }

        public UserId getUserId() {
            return userId;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private @Valid @NotNull UserId userId;

            private Builder() {
            }

            public Builder withUserId(@Valid @NotNull UserId userId) {
                this.userId = userId;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }

    final class Response {
        private final Username username;
        private final Euros balance;

        private Response(Builder builder) {
            username = builder.username;
            balance = builder.balance;
        }

        public Username getUsername() {
            return username;
        }

        public Euros getBalance() {
            return balance;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private Username username;
            private Euros balance;

            private Builder() {
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
