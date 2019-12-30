package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.model.snapshot.BetSnapshot;
import org.pureacc.betcentral.infra.jpa.model.BetEntity;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;

public class ToBet {
	public static Bet map(BetEntity betEntity) {
		BetSnapshot betSnapshot = BetSnapshot.newBuilder()
				.withBetId(BetId.of(betEntity.getId()))
				.withUser(ToUser.map(betEntity.getUser()))
				.withOdds(DecimalOdds.of(betEntity.getOdds()))
				.withEuros(Euros.of(betEntity.getEuros()))
				.withPlacedDate(betEntity.getPlacedDate())
				.withResolveDate(betEntity.getResolveDate())
				.withStatus(betEntity.getStatus())
				.build();
		return new Bet(betSnapshot);
	}
}
