package org.pureacc.betcentral.vocabulary;

import java.util.Objects;

import javax.validation.constraints.Min;

public final class DecimalOdds {
	@Min(1)
	private final double value;

	private DecimalOdds(double value) {
		this.value = value;
	}

	public static DecimalOdds of(double odds) {
		return new DecimalOdds(odds);
	}

	public double getValue() {
		return value;
	}

	public Percentage getProbability() {
		return Percentage.of(100.0 / value);
	}

	public Euros calculate(Euros euros) {
		return Euros.of(euros.getValue() * value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DecimalOdds that = (DecimalOdds) o;
		return Double.compare(that.value, value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
