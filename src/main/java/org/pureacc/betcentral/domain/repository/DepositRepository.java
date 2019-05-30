package org.pureacc.betcentral.domain.repository;

import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.vocabulary.UserId;

import java.util.List;

public interface DepositRepository {
    List<Deposit> findByUser(UserId userId);

    void save(Deposit deposit);
}
