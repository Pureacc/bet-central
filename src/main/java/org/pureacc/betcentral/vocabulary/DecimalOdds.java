package org.pureacc.betcentral.vocabulary;

import java.util.Objects;

import javax.validation.constraints.Min;

public final class DecimalOdds {
	@Min(1)
	private final double odds;

	private DecimalOdds(double odds) {
		this.odds = odds;
	}

	public static DecimalOdds of(double odds) {
		return new DecimalOdds(odds);
	}

	public double getOdds() {
		return odds;
	}

	public Percentage getProbability() {
		return Percentage.of(100.0 / odds);
	}

	public Euros calculate(Euros euros) {
		return Euros.of(euros.getValue() * odds);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DecimalOdds that = (DecimalOdds) o;
		return Double.compare(that.odds, odds) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(odds);
	}

	@Override
	public String toString() {
		return String.valueOf(odds);
	}
}
