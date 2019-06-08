package org.pureacc.betcentral.application.factory;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.vocabulary.Username;

public class Users {
    private final UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User aUser() {
        User user = new User(Username.of("John Doe"));
        return userRepository.save(user);
    }
}
