package org.pureacc.betcentral.application.api;

import org.pureacc.betcentral.vocabulary.BetId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface LoseBet {
    void execute(@Valid Request request);

    final class Request {
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
