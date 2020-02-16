package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.PlaceWithdrawal;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.model.Withdrawal;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.domain.repository.WithdrawalRepository;
import org.pureacc.betcentral.vocabulary.annotation.Command;

@Command
class PlaceWithdrawalCommand implements PlaceWithdrawal {
	private final WithdrawalRepository withdrawalRepository;
	private final UserRepository userRepository;

	PlaceWithdrawalCommand(WithdrawalRepository withdrawalRepository, UserRepository userRepository) {
		this.withdrawalRepository = withdrawalRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void execute(Request request) {
		User user = userRepository.get(request.getUserId());
		Withdrawal withdrawal = new Withdrawal(user, request.getEuros());
		withdrawalRepository.save(withdrawal);
	}
}
