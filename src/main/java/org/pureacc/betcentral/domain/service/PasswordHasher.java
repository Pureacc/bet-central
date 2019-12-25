package org.pureacc.betcentral.domain.service;

import org.pureacc.betcentral.vocabulary.Password;

public interface PasswordHasher {
	Password hash(Password password);
}
