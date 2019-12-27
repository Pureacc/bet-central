package org.pureacc.betcentral.infra.exceptionhandling;

import org.pureacc.betcentral.application.command.Command;
import org.pureacc.betcentral.vocabulary.exception.DomainException;
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
