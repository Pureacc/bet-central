package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;

public class User {
    private UserId userId;
    private Username username;
    private Balance balance;

    public User(Username username) {
        this.username = username;
        this.balance = new Balance();
    }

    public User(UserId userId, Username username, Balance balance) {
        this.userId = userId;
        this.username = username;
        this.balance = balance;
    }

    public boolean isBalanceSufficient(Euros euros) {
        return balance.isSufficient(euros);
    }

    public void deposit(Euros euros) {
        balance.add(euros);
    }

    public UserId getId() {
        return userId;
    }

    public Username getUsername() {
        return username;
    }

    public Balance getBalance() {
        return balance;
    }
}
