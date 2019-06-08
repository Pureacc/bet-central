package org.pureacc.betcentral.domain.repository;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;

public interface UserRepository {
    User get(UserId userId);

    User get(Username username);

    User save(User user);
}
