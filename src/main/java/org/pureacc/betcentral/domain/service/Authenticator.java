package org.pureacc.betcentral.domain.service;

import org.pureacc.betcentral.domain.model.User;

public interface Authenticator {
	void authenticate(User user);
}
