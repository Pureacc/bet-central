package org.pureacc.betcentral.infra.jpa.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String username;
    @Embedded
    private BalanceEntity balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BalanceEntity getBalance() {
        return balance;
    }

    public void setBalance(BalanceEntity balance) {
        this.balance = balance;
    }
}
