package org.pureacc.betcentral.domain.repository;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.vocabulary.UserId;

public interface UserRepository {
    User get(UserId userId);

    User get(String username);

    void save(User user);
}
