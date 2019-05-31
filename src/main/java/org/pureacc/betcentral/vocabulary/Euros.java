package org.pureacc.betcentral.vocabulary;

import javax.validation.constraints.Positive;
import java.util.Objects;

public class Euros {
    @Positive
    private double value;

    private Euros(double value) {
        this.value = value;
    }

    public void add(Euros euros) {
        this.value += euros.value;
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
