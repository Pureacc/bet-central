package org.pureacc.betcentral.domain.repository;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.vocabulary.BetId;

public interface BetRepository {
    Bet get(BetId betId);

    Bet save(Bet bet);
}
