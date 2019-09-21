package org.pureacc.betcentral.vocabulary;

import javax.validation.constraints.Positive;
import java.util.Objects;

public final class BetId {
    @Positive
    private final long value;

    public BetId(@Positive long value) {
        this.value = value;
    }

    public static BetId of(long value) {
        return new BetId(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetId betId = (BetId) o;
        return value == betId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
