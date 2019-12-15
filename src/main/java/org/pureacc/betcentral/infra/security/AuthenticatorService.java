package org.pureacc.betcentral.infra.security;

import static java.util.Collections.emptyList;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.service.Authenticator;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class AuthenticatorService implements Authenticator {
	@Override
	public void authenticate(User user) {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken(user.getUsername(), "password", emptyList()));
	}
}
