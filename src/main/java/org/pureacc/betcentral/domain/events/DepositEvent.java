package org.pureacc.betcentral.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;

public final class DepositEvent implements Event {
    private final UserId userId;
    private final Euros euros;

    @JsonCreator
    public DepositEvent(@JsonProperty("userId") UserId userId, @JsonProperty("euros") Euros euros) {
        this.userId = userId;
        this.euros = euros;
    }

    public UserId getUserId() {
        return userId;
    }

    public Euros getEuros() {
        return euros;
    }
}
