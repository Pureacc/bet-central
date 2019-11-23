package org.pureacc.betcentral.infra.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.pureacc.betcentral.infra.security.checks.IsAuthenticated;
import org.pureacc.betcentral.vocabulary.exception.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class SecurityControl {
	private final IsAuthenticated isAuthenticated;

	public SecurityControl(IsAuthenticated isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public void check(Security... security) {
		Set<Security.Require> requires = new HashSet<>();
		for (Security s : security) {
			requires.addAll(Arrays.asList(s.value()));
			requires.removeAll(Arrays.asList(s.drop()));
		}
		validate(requires);
	}

	private void validate(Set<Security.Require> requires) {
		if (requires.contains(Security.Require.AUTHENTICATED)) {
			validateAuthenticated();
		}
	}

	private void validateAuthenticated() {
		if (!isAuthenticated.isAuthenticated()) {
			throw new AccessDeniedException();
		}
	}
}
