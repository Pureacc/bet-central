package org.pureacc.betcentral.vocabulary;

import javax.validation.constraints.NotBlank;

public final class Password {
	@NotBlank
	private final String value;

	private Password(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Password of(String value) {
		return new Password(value);
	}
}
