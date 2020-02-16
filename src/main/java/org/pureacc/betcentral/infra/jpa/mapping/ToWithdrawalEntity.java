package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Withdrawal;
import org.pureacc.betcentral.domain.model.snapshot.WithdrawalSnapshot;
import org.pureacc.betcentral.infra.jpa.model.WithdrawalEntity;

public class ToWithdrawalEntity {
	public static WithdrawalEntity map(Withdrawal withdrawal) {
		WithdrawalSnapshot withdrawalSnapshot = withdrawal.toSnapshot();
		WithdrawalEntity withdrawalEntity = new WithdrawalEntity();
		withdrawalEntity.setUser(ToUserEntity.map(withdrawalSnapshot.getUser()));
		withdrawalEntity.setEuros(withdrawalSnapshot.getEuros()
				.getValue());
		withdrawalEntity.setDate(withdrawalSnapshot.getDate());
		return withdrawalEntity;
	}
}
