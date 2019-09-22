package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.domain.model.snapshot.BetSnapshot;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.BetStatus;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.exception.DomainException;

import java.util.Date;

import static org.pureacc.betcentral.domain.service.DomainTime.now;
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
            this.placedDate = now();
            this.status = PENDING;
        } else {
            throw new DomainException();
        }
    }

    public Bet(BetSnapshot betSnapshot) {
        this.betId = betSnapshot.getBetId();
        this.user = betSnapshot.getUser();
        this.odds = betSnapshot.getOdds();
        this.euros = betSnapshot.getEuros();
        this.placedDate = betSnapshot.getPlacedDate();
        this.resolveDate = betSnapshot.getResolveDate();
        this.status = betSnapshot.getStatus();
    }

    public BetSnapshot toSnapshot() {
        return BetSnapshot.newBuilder()
                .withBetId(betId)
                .withUser(user)
                .withOdds(odds)
                .withEuros(euros)
                .withPlacedDate(placedDate)
                .withResolveDate(resolveDate)
                .withStatus(status)
                .build();
    }

    public BetId getId() {
        return betId;
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
        resolveDate = now();
        status = WON;
    }

    public void lose() {
        if (status != PENDING) {
            throw new DomainException();
        }
        resolveDate = now();
        status = LOST;
    }
}
