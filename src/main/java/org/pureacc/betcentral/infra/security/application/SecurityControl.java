package org.pureacc.betcentral.infra.security.application;

import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.AUTHENTICATED;
import static org.pureacc.betcentral.vocabulary.annotation.Allow.Role.UNAUTHENTICATED;

import java.util.Arrays;
import java.util.List;

import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.pureacc.betcentral.vocabulary.annotation.Allow.Role;
import org.pureacc.betcentral.infra.security.application.checks.IsAuthenticated;
import org.pureacc.betcentral.vocabulary.exception.AccessDeniedException;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class SecurityControl {
	private final IsAuthenticated isAuthenticated;

	SecurityControl(IsAuthenticated isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
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
		if (role == UNAUTHENTICATED) {
			return !isAuthenticated.isAuthenticated();
		}
		if (role == AUTHENTICATED) {
			return isAuthenticated.isAuthenticated();
		}
		throw new SystemException("Unknown role " + role);
	}
}
