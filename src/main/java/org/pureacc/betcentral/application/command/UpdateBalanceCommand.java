package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.UpdateBalance;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;

@Command
class UpdateBalanceCommand implements UpdateBalance {
	private final UserRepository userRepository;

	UpdateBalanceCommand(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void execute(Request request) {
		User user = userRepository.get(request.getUserId());
		user.updateBalance(request.getEuros(), request.getOperation());
		userRepository.save(user);
	}
}
