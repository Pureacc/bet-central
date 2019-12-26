package org.pureacc.betcentral.infra.security.application.checks;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HasAuthority {
	public boolean hasAuthority(Authority authority) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication != null && authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch((a) -> a.equals(authority.name()));
	}

	public enum Authority {
		SYSTEM
	}
}
