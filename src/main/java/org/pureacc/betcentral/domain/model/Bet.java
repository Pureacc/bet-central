package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.exception.DomainException;

import java.util.Date;

public class Bet {
    private BetId betId;
    private User user;
    private DecimalOdds odds;
    private Euros euros;
    private Date placedDate;

    public Bet(User user, DecimalOdds odds, Euros euros) {
        if (user.isBalanceSufficient(euros)) {
            this.user = user;
            this.odds = odds;
            this.euros = euros;
            this.placedDate = new Date();
        } else {
            throw new DomainException();
        }
    }

    public Bet(BetId betId, User user, DecimalOdds odds, Euros euros, Date placedDate) {
        this.betId = betId;
        this.user = user;
        this.odds = odds;
        this.euros = euros;
        this.placedDate = placedDate;
    }

    public BetId getId() {
        return betId;
    }

    public User getUser() {
        return user;
    }

    public DecimalOdds getOdds() {
        return odds;
    }

    public Euros getEuros() {
        return euros;
    }

    public Date getPlacedDate() {
        return placedDate;
    }
}
