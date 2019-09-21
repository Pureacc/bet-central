package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.BetStatus;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.exception.DomainException;

import java.util.Date;

import static org.pureacc.betcentral.vocabulary.BetStatus.*;

public class Bet {
    private BetId betId;
    private User user;
    private DecimalOdds odds;
    private Euros euros;
    private Date placedDate;
    private Date resolveDate;
    private BetStatus status;

    public Bet(User user, DecimalOdds odds, Euros euros) {
        if (user.isBalanceSufficient(euros)) {
            this.user = user;
            this.odds = odds;
            this.euros = euros;
            this.placedDate = new Date();
            this.status = PENDING;
        } else {
            throw new DomainException();
        }
    }

    public Bet(BetId betId, User user, DecimalOdds odds, Euros euros, Date placedDate, Date resolveDate, BetStatus status) {
        this.betId = betId;
        this.user = user;
        this.odds = odds;
        this.euros = euros;
        this.placedDate = placedDate;
        this.resolveDate = resolveDate;
        this.status = status;
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

    public Date getResolveDate() {
        return resolveDate;
    }

    public BetStatus getStatus() {
        return status;
    }

    public boolean isWon() {
        return status == WON;
    }

    public boolean isLost() {
        return status == LOST;
    }

    public void win() {
        if (status != PENDING) {
            throw new DomainException();
        }
        resolveDate = new Date();
        status = WON;
    }

    public void lose() {
        if (status != PENDING) {
            throw new DomainException();
        }
        resolveDate = new Date();
        status = LOST;
    }
}
