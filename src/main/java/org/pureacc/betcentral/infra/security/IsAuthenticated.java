package org.pureacc.betcentral.infra.security;

import org.springframework.stereotype.Component;

@Component
public class IsAuthenticated {
	public boolean isAuthenticated() {
		return false;
	}
}
