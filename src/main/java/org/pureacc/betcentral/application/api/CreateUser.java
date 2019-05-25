package org.pureacc.betcentral.application.api;

public interface CreateUser {
    void execute(Request request);

    final class Request {
        private final String username;

        private Request(Builder builder) {
            username = builder.username;
        }

        public String getUsername() {
            return username;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static final class Builder {
            private String username;

            private Builder() {
            }

            public Builder withUsername(String username) {
                this.username = username;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }
}
