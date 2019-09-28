package org.pureacc.betcentral.vocabulary;

import java.util.Objects;

import javax.validation.constraints.Positive;

public class Euros {
    @Positive
    private double value;

    private Euros(double value) {
        this.value = value;
    }

    public void add(Euros euros) {
        this.value += euros.value;
    }

    public void substract(Euros euros) {
        this.value -= euros.value;
    }

    public boolean isAtLeast(Euros euros) {
        return value >= euros.value;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public double getValue() {
        return value;
    }

    public static Euros of(double value) {
        return new Euros(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Euros euros = (Euros) o;
        return Double.compare(euros.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
