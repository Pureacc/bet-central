package org.pureacc.betcentral.domain.model;

import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Percentage;

public class Vig {
    private Percentage percentage;

    public Vig(DecimalOdds oddsA, DecimalOdds oddsB) {
        Percentage impliedProbabilityA = oddsA.getProbability();
        Percentage impliedProbabilityB = oddsB.getProbability();
        percentage = impliedProbabilityA.add(impliedProbabilityB).substract(Percentage.of(100));
    }

    public Percentage getPercentage() {
        return percentage;
    }
}
