package org.pureacc.betcentral.infra.security.application;

import org.pureacc.betcentral.application.command.Command;

@Command
public class TestCommandImpl implements TestCommand {
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
}