package org.pureacc.betcentral.application.factory;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.vocabulary.UserId;

public class Users {
    private final UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User aUser() {
        User user = new User(new UserId(1L), "John Doe", new Balance());
        userRepository.save(user);
        return user;
    }
}
