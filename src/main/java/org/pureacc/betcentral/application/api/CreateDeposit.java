package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.infra.security.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.infra.security.Allow;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public interface CreateDeposit {
    @Allow(AUTHENTICATED)
    Response execute(@Valid Request request);

    final class Request {
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

    final class Response {
        private final Euros balance;

        private Response(Builder builder) {
            balance = builder.balance;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Euros getBalance() {
            return balance;
        }

        public static final class Builder {
            private Euros balance;

            private Builder() {
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
