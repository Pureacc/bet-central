package infra.exceptionhandling;

import org.pureacc.betcentral.vocabulary.annotation.Command;
import org.pureacc.betcentral.domain.DomainException;
import org.pureacc.betcentral.vocabulary.exception.SystemException;

@Command
public class TestCommandImpl implements TestCommand {
	@Override
	public void domainException(String key, Object... params) {
		throw new DomainException(key, params);
	}

	@Override
	public void systemException() {
		throw new SystemException("An error occurred");
	}
}
