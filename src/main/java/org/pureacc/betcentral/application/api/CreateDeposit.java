package org.pureacc.betcentral.application.api;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public interface CreateDeposit {
    void execute(Request request);

    final class Request {
        private final UserId userId;
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
