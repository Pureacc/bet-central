package org.pureacc.betcentral.domain.repository;

import java.util.List;

import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.vocabulary.UserId;

public interface DepositRepository {
	List<Deposit> findByUser(UserId userId);

	void save(Deposit deposit);
}
