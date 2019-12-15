package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.Authenticate;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.domain.service.Authenticator;

@Command
class AuthenticateCommand implements Authenticate {
	private final UserRepository userRepository;
	private final Authenticator authenticator;

	AuthenticateCommand(UserRepository userRepository, Authenticator authenticator) {
		this.userRepository = userRepository;
		this.authenticator = authenticator;
	}

	@Override
	public Response execute(Request request) {
		User user = userRepository.get(request.getUsername());
		authenticator.authenticate(user);
		return Response.newBuilder()
				.withUserId(user.getId())
				.withUsername(user.getUsername())
				.withBalance(user.getBalance()
						.getEuros())
				.build();
	}
}
