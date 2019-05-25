package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
class CreateUserCommand implements CreateUser {
    private final UserRepository userRepository;

    CreateUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void execute(Request request) {
        User user = new User(request.getUsername());
        userRepository.save(user);
    }
}
