package org.pureacc.betcentral.application.api;

import static org.pureacc.betcentral.infra.security.Allow.Role.UNAUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.betcentral.infra.security.Allow;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;

public interface CreateUser {
    @Allow(UNAUTHENTICATED)
    Response execute(@Valid Request request);

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

    final class Response {
        private final UserId userId;

        private Response(Builder builder) {
            userId = builder.userId;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public UserId getUserId() {
            return userId;
        }

        public static final class Builder {
            private UserId userId;

            private Builder() {
            }

            public Builder withUserId(UserId userId) {
                this.userId = userId;
                return this;
            }

            public Response build() {
                return new Response(this);
            }
        }
    }
}
