package org.pureacc.betcentral.infra.jpa.model;

import javax.persistence.Embeddable;

@Embeddable
public class BalanceEntity {
    private double euros;

    public double getEuros() {
        return euros;
    }

    public void setEuros(double euros) {
        this.euros = euros;
    }
}
