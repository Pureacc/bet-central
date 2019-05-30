package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.domain.events.DepositEvent;
import org.pureacc.betcentral.domain.service.DomainEventPublisher;
import org.pureacc.betcentral.vocabulary.Euros;

import java.util.Date;

public class Deposit {
    private User user;
    private Euros euros;
    private Date date;

    public Deposit(User user, Euros euros) {
        this.user = user;
        this.euros = euros;
        this.date = new Date();
        DepositEvent event = new DepositEvent(user.getUserId(), euros);
        DomainEventPublisher.publish(event);
    }

    public Deposit(User user, Euros euros, Date date) {
        this.user = user;
        this.euros = euros;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public Euros getEuros() {
        return euros;
    }

    public Date getDate() {
        return date;
    }
}
