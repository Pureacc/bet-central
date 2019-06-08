package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public class User {
    private UserId userId;
    private String username;
    private Balance balance;

    public User(String username) {
        this.username = username;
        this.balance = new Balance();
    }

    public User(UserId userId, String username, Balance balance) {
        this.userId = userId;
        this.username = username;
        this.balance = balance;
    }

    public void deposit(Euros euros) {
        balance.add(euros);
    }

    public UserId getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Balance getBalance() {
        return balance;
    }
}
