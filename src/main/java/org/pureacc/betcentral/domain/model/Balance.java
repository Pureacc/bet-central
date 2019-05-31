package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.Euros;

public class Balance {
    private Euros euros;

    public Balance() {
        this.euros = Euros.of(0);
    }

    public Balance(Euros euros) {
        this.euros = euros;
    }

    public void add(Euros euros) {
        this.euros.add(euros);
    }

    public boolean isEmpty() {
        return euros.isEmpty();
    }

    public Euros getEuros() {
        return euros;
    }
}
