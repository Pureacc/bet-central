package org.pureacc.betcentral.vocabulary;

import java.util.Objects;

public final class Percentage {
    private double value;

    private Percentage(double value) {
        this.value = value;
    }

    public static Percentage of(double value) {
        return new Percentage(value);
    }

    public double getValue() {
        return value;
    }

    public DecimalOdds getDecimalOdds() {
        return DecimalOdds.of(100.0 / value);
    }

    public Percentage add(Percentage percentage) {
        this.value += percentage.value;
        return this;
    }

    public Percentage substract(Percentage percentage) {
        this.value -= percentage.value;
        return this;
    }

    public Percentage divide(Percentage percentage) {
        this.value = 100 * this.value / percentage.value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Percentage that = (Percentage) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + "%";
    }
}
