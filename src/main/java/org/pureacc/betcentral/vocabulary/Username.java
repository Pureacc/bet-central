package org.pureacc.betcentral.vocabulary;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public final class Username {
    @Size(min = 8, max = 32)
    @NotBlank
    private final String value;

    private Username(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Username of(String value) {
        return new Username(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
