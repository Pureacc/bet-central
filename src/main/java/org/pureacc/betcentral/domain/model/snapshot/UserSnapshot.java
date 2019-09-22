package org.pureacc.betcentral.domain.model.snapshot;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;

public final class UserSnapshot {
    private final UserId userId;
    private final Username username;
    private final Balance balance;

    private UserSnapshot(Builder builder) {
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

    public Balance getBalance() {
        return balance;
    }

    public static final class Builder {
        private UserId userId;
        private Username username;
        private Balance balance;

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

        public Builder withBalance(Balance balance) {
            this.balance = balance;
            return this;
        }

        public UserSnapshot build() {
            return new UserSnapshot(this);
        }
    }
}
