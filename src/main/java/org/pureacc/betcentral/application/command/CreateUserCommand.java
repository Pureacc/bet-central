package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.domain.service.PasswordHasher;
import org.pureacc.betcentral.vocabulary.annotation.Command;

@Command
class CreateUserCommand implements CreateUser {
	private final UserRepository userRepository;
	private final PasswordHasher passwordHasher;

	CreateUserCommand(UserRepository userRepository, PasswordHasher passwordHasher) {
		this.userRepository = userRepository;
		this.passwordHasher = passwordHasher;
	}

	@Override
	public Response execute(Request request) {
		User user = new User(request.getUsername(), request.getPassword(), passwordHasher);
		User savedUser = userRepository.save(user);
		return Response.newBuilder()
				.withUserId(savedUser.getId())
				.build();
	}
}
