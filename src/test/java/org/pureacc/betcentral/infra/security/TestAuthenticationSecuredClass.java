package org.pureacc.betcentral.infra.security;

@Security
public class TestAuthenticationSecuredClass {
	public boolean method() {
		return true;
	}

	@Security
	public boolean authenticationSecuredMethod() {
		return true;
	}

	@Security(drop = Security.Require.AUTHENTICATED)
	public boolean authenticationDroppedSecuredMethod() {
		return true;
	}
}
