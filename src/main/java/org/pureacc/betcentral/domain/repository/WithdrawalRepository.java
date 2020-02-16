package org.pureacc.betcentral.domain.repository;

import java.util.List;

import org.pureacc.betcentral.domain.model.Withdrawal;
import org.pureacc.betcentral.vocabulary.UserId;

public interface WithdrawalRepository {
	List<Withdrawal> findByUser(UserId userId);

	void save(Withdrawal withdrawal);
}
