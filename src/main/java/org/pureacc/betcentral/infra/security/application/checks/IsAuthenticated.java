package org.pureacc.betcentral.infra.security.application.checks;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IsAuthenticated {
	private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication != null && authentication.isAuthenticated() && !isAnonymous(authentication);
	}

	private boolean isAnonymous(Authentication authentication) {
		return authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(ROLE_ANONYMOUS::equals);
	}
}
