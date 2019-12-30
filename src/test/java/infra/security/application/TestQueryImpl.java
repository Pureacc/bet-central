package infra.security.application;

import org.pureacc.betcentral.vocabulary.annotation.Query;

@Query
public class TestQueryImpl implements TestQuery {
	@Override
	public boolean allowNone() {
		return true;
	}

	@Override
	public boolean allowUnauthenticated() {
		return true;
	}

	@Override
	public boolean allowAuthenticated() {
		return true;
	}

	@Override
	public boolean allowUnauthenticatedAndAuthenticated() {
		return true;
	}

	@Override
	public boolean allowSystem() {
		return true;
	}
}
