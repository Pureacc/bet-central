package org.pureacc.betcentral.infra.security.application;

import java.util.Arrays;
import java.util.List;

import org.pureacc.betcentral.infra.security.application.checks.HasAuthority;
import org.pureacc.betcentral.infra.security.application.checks.HasAuthority.Authority;
import org.pureacc.betcentral.infra.security.application.checks.IsAuthenticated;
import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.pureacc.betcentral.vocabulary.annotation.Allow.Role;
import org.pureacc.betcentral.infra.security.AccessDeniedException;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class SecurityControl {
	private final IsAuthenticated isAuthenticated;
	private final HasAuthority hasAuthority;

	SecurityControl(IsAuthenticated isAuthenticated, HasAuthority hasAuthority) {
		this.isAuthenticated = isAuthenticated;
		this.hasAuthority = hasAuthority;
	}

	public void check(Allow allow) {
		if (allow == null || !hasAtLeastOneRole(Arrays.asList(allow.value()))) {
			throw new AccessDeniedException();
		}
	}

	private boolean hasAtLeastOneRole(List<Role> roles) {
		return roles.stream()
				.anyMatch(this::hasRole);
	}

	private boolean hasRole(Role role) {
		switch (role) {
		case UNAUTHENTICATED:
			return !isAuthenticated.isAuthenticated();
		case AUTHENTICATED:
			return isAuthenticated.isAuthenticated();
		case SYSTEM:
			return hasAuthority.hasAuthority(Authority.SYSTEM);
		default:
			throw new SystemException("Unknown role " + role);
		}
	}
}
