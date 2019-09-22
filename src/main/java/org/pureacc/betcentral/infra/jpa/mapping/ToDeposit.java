package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.model.snapshot.DepositSnapshot;
import org.pureacc.betcentral.infra.jpa.model.DepositEntity;
import org.pureacc.betcentral.vocabulary.Euros;

public class ToDeposit {
    public static Deposit map(DepositEntity depositEntity) {
        DepositSnapshot depositSnapshot = DepositSnapshot.newBuilder()
                .withUser(ToUser.map(depositEntity.getUser()))
                .withEuros(Euros.of(depositEntity.getEuros()))
                .withDate(depositEntity.getDate())
                .build();
        return new Deposit(depositSnapshot);
    }
}
