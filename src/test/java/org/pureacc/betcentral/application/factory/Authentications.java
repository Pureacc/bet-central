package org.pureacc.betcentral.application.factory;

import static java.util.Collections.emptyList;

import org.pureacc.betcentral.domain.model.User;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authentications {
	public static void authenticate(User user) {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken(user.getUsername(), "password", emptyList()));
	}

	public static void unauthenticate() {
		SecurityContextHolder.getContext()
				.setAuthentication(null);
	}
}
