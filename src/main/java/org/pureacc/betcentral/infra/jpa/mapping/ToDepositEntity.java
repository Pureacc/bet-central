package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.model.snapshot.DepositSnapshot;
import org.pureacc.betcentral.infra.jpa.model.DepositEntity;

public class ToDepositEntity {
    public static DepositEntity map(Deposit deposit) {
        DepositSnapshot depositSnapshot = deposit.toSnapshot();
        DepositEntity depositEntity = new DepositEntity();
        depositEntity.setUser(ToUserEntity.map(depositSnapshot.getUser()));
        depositEntity.setEuros(depositSnapshot.getEuros().getValue());
        depositEntity.setDate(depositSnapshot.getDate());
        return depositEntity;
    }
}
