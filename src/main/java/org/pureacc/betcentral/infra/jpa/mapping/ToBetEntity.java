package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.model.snapshot.BetSnapshot;
import org.pureacc.betcentral.infra.jpa.model.BetEntity;

public class ToBetEntity {
	public static BetEntity map(Bet bet) {
		BetSnapshot betSnapshot = bet.toSnapshot();
		BetEntity betEntity = new BetEntity();
		if (betSnapshot.getBetId() != null) {
			betEntity.setId(betSnapshot.getBetId()
					.getValue());
		}
		betEntity.setUser(ToUserEntity.map(betSnapshot.getUser()));
		betEntity.setOdds(betSnapshot.getOdds()
				.getValue());
		betEntity.setEuros(betSnapshot.getEuros()
				.getValue());
		betEntity.setPlacedDate(betSnapshot.getPlacedDate());
		betEntity.setResolveDate(betSnapshot.getResolveDate());
		betEntity.setStatus(betSnapshot.getStatus());
		return betEntity;
	}
}
