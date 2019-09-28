package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;

@Command
class CreateUserCommand implements CreateUser {
    private final UserRepository userRepository;

    CreateUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        User user = new User(request.getUsername());
        User savedUser = userRepository.save(user);
        return Response.newBuilder()
                .withUserId(savedUser.getId())
                .build();
    }
}
