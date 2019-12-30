package org.pureacc.betcentral.infra.security.application;

import static java.lang.String.format;

import org.pureacc.betcentral.infra.security.AccessDeniedException;
import org.pureacc.betcentral.infra.security.web.AuthenticationService;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class ResourceSecurityControl {
	private final AuthenticationService authenticationService;

	ResourceSecurityControl(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void check(Object resourceValue) {
		UserId authenticatedUserId = authenticationService.getAuthenticatedUserId();
		if (!isAuthorized(authenticatedUserId, resourceValue)) {
			throw new AccessDeniedException();
		}
	}

	private boolean isAuthorized(UserId authenticatedUserId, Object resourceValue) {
		if (resourceValue.getClass() == UserId.class) {
			return authenticatedUserId.equals(resourceValue);
		}
		throw new SystemException(
				format("Missing security rule for secured resource with type '%s'", resourceValue.getClass()));
	}
}
