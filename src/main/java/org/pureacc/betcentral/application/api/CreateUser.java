package org.pureacc.betcentral.application.api;

import org.pureacc.betcentral.vocabulary.Username;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateUser {
    void execute(@Valid Request request);

    final class Request {
        @Valid
        @NotNull
        private final Username username;

        private Request(Builder builder) {
            username = builder.username;
        }

        public Username getUsername() {
            return username;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private Username username;

            private Builder() {
            }

            public Builder withUsername(Username username) {
                this.username = username;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }
}
