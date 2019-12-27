package infra.exceptionhandling;

import org.pureacc.betcentral.application.query.Query;
import org.pureacc.betcentral.vocabulary.exception.DomainException;
import org.pureacc.betcentral.vocabulary.exception.SystemException;

@Query
public class TestQueryImpl implements TestQuery {
	@Override
	public void domainException(String key, Object... params) {
		throw new DomainException(key, params);
	}

	@Override
	public void systemException() {
		throw new SystemException("An error occurred");
	}
}
