package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.Authenticate;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;

@Command
class AuthenticateCommand implements Authenticate {
	private final UserRepository userRepository;

	AuthenticateCommand(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Response execute(Request request) {
		User user = userRepository.get(request.getUsername());
		return Response.newBuilder()
				.withUserId(user.getId())
				.withUsername(user.getUsername())
				.withBalance(user.getBalance()
						.getEuros())
				.build();
	}
}
