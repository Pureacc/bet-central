package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;

public class ToBalanceEntity {
    public static BalanceEntity map(Balance balance) {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setEuros(balance.getEuros().getValue());
        return balanceEntity;
    }
}
