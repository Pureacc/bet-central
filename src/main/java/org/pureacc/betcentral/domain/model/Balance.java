package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.exception.DomainException;

public class Balance {
    private Euros euros;

    Balance() {
        this.euros = Euros.of(0);
    }

    public Balance(Euros euros) {
        this.euros = euros;
    }

    public boolean isEmpty() {
        return euros.isEmpty();
    }

    public Euros getEuros() {
        return euros;
    }

    void add(Euros euros) {
        this.euros.add(euros);
    }

    void substract(Euros euros) {
        validateSufficient(euros);
        this.euros.substract(euros);
    }

    void validateSufficient(Euros euros) {
        if (this.euros.isLessThan(euros)) {
            throw new DomainException("user.balance.insufficient", this.euros);
        }
    }
}
