package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;
import org.pureacc.betcentral.vocabulary.Euros;

public class ToBalance {
	public static Balance map(BalanceEntity balanceEntity) {
		return new Balance(Euros.of(balanceEntity.getEuros()));
	}
}
