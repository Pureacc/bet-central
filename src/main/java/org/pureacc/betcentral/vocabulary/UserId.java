package org.pureacc.betcentral.vocabulary;

import javax.validation.constraints.Positive;
import java.util.Objects;

public final class UserId {
    @Positive
    private final long value;

    public UserId(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return value == userId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
