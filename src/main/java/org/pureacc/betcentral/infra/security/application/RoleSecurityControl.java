package org.pureacc.betcentral.infra.security.application;

import java.util.Arrays;
import java.util.List;

import org.pureacc.betcentral.infra.security.AccessDeniedException;
import org.pureacc.betcentral.infra.security.web.AuthenticationService;
import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.pureacc.betcentral.vocabulary.annotation.Allow.Role;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class RoleSecurityControl {
	private final AuthenticationService authenticationService;

	RoleSecurityControl(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
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
			return !authenticationService.isAuthenticated();
		case AUTHENTICATED:
			return authenticationService.isAuthenticated();
		case SYSTEM:
			return authenticationService.hasAuthority(AuthenticationService.Authority.SYSTEM);
		default:
			throw new SystemException("Unknown role " + role);
		}
	}
}
