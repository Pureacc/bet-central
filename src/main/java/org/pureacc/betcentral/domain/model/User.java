package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.Operation;
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

    public User(UserSnapshot userSnapshot) {
        this.userId = userSnapshot.getUserId();
        this.username = userSnapshot.getUsername();
        this.balance = userSnapshot.getBalance();
    }

    public UserSnapshot toSnapshot() {
        return UserSnapshot.newBuilder()
                .withUserId(userId)
                .withUsername(username)
                .withBalance(balance)
                .build();
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

    public void updateBalance(Euros euros, Operation operation) {
        switch (operation) {
        case ADD:
            balance.add(euros);
            break;
        case SUBSTRACT:
            balance.substract(euros);
            break;
        }
    }

    boolean isBalanceSufficient(Euros euros) {
        return balance.isSufficient(euros);
    }
}
