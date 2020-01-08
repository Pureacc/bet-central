package org.pureacc.betcentral.infra.security.application;

import static java.lang.String.format;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.infra.security.AccessDeniedException;
import org.pureacc.betcentral.infra.security.web.AuthenticationService;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class ResourceSecurityControl {
	private final AuthenticationService authenticationService;
	private final BetRepository betRepository;

	ResourceSecurityControl(AuthenticationService authenticationService, BetRepository betRepository) {
		this.authenticationService = authenticationService;
		this.betRepository = betRepository;
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
		} else if (resourceValue.getClass() == BetId.class) {
			Bet bet = betRepository.get((BetId) resourceValue);
			return bet.isPlacedBy(authenticatedUserId);
		}
		throw new SystemException(
				format("Missing security rule for secured resource with type '%s'", resourceValue.getClass()));
	}
}
