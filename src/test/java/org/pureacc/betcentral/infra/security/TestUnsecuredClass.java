package org.pureacc.betcentral.infra.security;

public class TestUnsecuredClass {
	@Security
	public boolean authenticationSecuredMethod() {
		return true;
	}
}
