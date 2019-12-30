package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Percentage;

public class BetOffer {
	private DecimalOdds oddsA;
	private DecimalOdds oddsB;
	private Vig vig;

	public BetOffer(DecimalOdds oddsA, DecimalOdds oddsB) {
		this.oddsA = oddsA;
		this.oddsB = oddsB;
		this.vig = new Vig(oddsA, oddsB);
	}

	public Vig getVig() {
		return vig;
	}

	public DecimalOdds getNoVigOddsA() {
		Percentage noVigProbabilityA = oddsA.getProbability()
				.divide(getOverallPercentMarket());
		return noVigProbabilityA.getDecimalOdds();
	}

	public DecimalOdds getNoVigOddsB() {
		Percentage noVigProbabilityB = oddsB.getProbability()
				.divide(getOverallPercentMarket());
		return noVigProbabilityB.getDecimalOdds();
	}

	private Percentage getOverallPercentMarket() {
		return Percentage.of(100)
				.add(vig.getPercentage());
	}
}
