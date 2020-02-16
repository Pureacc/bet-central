package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Withdrawal;
import org.pureacc.betcentral.domain.model.snapshot.WithdrawalSnapshot;
import org.pureacc.betcentral.infra.jpa.model.WithdrawalEntity;
import org.pureacc.betcentral.vocabulary.Euros;

public class ToWithdrawal {
	public static Withdrawal map(WithdrawalEntity withdrawalEntity) {
		WithdrawalSnapshot withdrawalSnapshot = WithdrawalSnapshot.newBuilder()
				.withUser(ToUser.map(withdrawalEntity.getUser()))
				.withEuros(Euros.of(withdrawalEntity.getEuros()))
				.withDate(withdrawalEntity.getDate())
				.build();
		return new Withdrawal(withdrawalSnapshot);
	}
}
