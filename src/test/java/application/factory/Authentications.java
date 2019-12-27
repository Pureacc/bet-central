package application.factory;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.infra.security.application.checks.HasAuthority;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authentications {
	public static void authenticate(User user) {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken(user.getUsername(), "password", emptyList()));
	}

	public static void authenticate(User user, HasAuthority.Authority authority) {
		SecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken(user.getUsername(), "password",
						singletonList(new SimpleGrantedAuthority(authority.name()))));
	}

	public static void unauthenticate() {
		SecurityContextHolder.getContext()
				.setAuthentication(null);
	}
}
