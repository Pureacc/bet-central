package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.infra.security.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.infra.security.Allow;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public interface PlaceBet {
    @Allow(AUTHENTICATED)
    Response execute(@Valid Request request);

    final class Request {
        @Valid
        @NotNull
        private final UserId userId;
        @Valid
        @NotNull
        private final DecimalOdds odds;
        @Valid
        @NotNull
        private final Euros euros;

        private Request(Builder builder) {
            userId = builder.userId;
            odds = builder.odds;
            euros = builder.euros;
        }

        public static Builder newBuilder() {
            return new Builder();
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

            public Request build() {
                return new Request(this);
            }
        }
    }

    final class Response {
        private final BetId betId;

        private Response(Builder builder) {
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

            public Response build() {
                return new Response(this);
            }
        }
    }
}
