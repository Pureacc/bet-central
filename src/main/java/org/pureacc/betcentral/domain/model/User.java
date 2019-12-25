package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot;
import org.pureacc.betcentral.domain.service.PasswordHasher;
import org.pureacc.betcentral.vocabulary.*;

public class User {
    private UserId userId;
    private Username username;
    private Password password;
    private Balance balance;

    public User(Username username, Password password, PasswordHasher passwordHasher) {
        this.username = username;
        this.password = passwordHasher.hash(password);
        this.balance = new Balance();
    }

    public User(UserSnapshot userSnapshot) {
        this.userId = userSnapshot.getUserId();
        this.username = userSnapshot.getUsername();
        this.password = userSnapshot.getPassword();
        this.balance = userSnapshot.getBalance();
    }

    public UserSnapshot toSnapshot() {
        return UserSnapshot.newBuilder()
                .withUserId(userId)
                .withUsername(username)
                .withPassword(password)
                .withBalance(balance)
                .build();
    }

    public UserId getId() {
        return userId;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
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
