package org.pureacc.betcentral.infra.security;

public class AccessDeniedException extends RuntimeException {
	public AccessDeniedException() {
		super("Access is denied");
	}
}
