package org.pureacc.betcentral.infra.security;

import static org.pureacc.betcentral.infra.security.Allow.Role.AUTHENTICATED;
import static org.pureacc.betcentral.infra.security.Allow.Role.UNAUTHENTICATED;

public interface TestCommand {
	boolean allowNone();

	@Allow(UNAUTHENTICATED)
	boolean allowUnauthenticated();

	@Allow(AUTHENTICATED)
	boolean allowAuthenticated();

	@Allow({UNAUTHENTICATED, AUTHENTICATED})
	boolean allowUnauthenticatedAndAuthenticated();
}
