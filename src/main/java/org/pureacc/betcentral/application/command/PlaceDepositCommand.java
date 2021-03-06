package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.PlaceDeposit;
import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.DepositRepository;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.vocabulary.annotation.Command;

@Command
class PlaceDepositCommand implements PlaceDeposit {
	private final DepositRepository depositRepository;
	private final UserRepository userRepository;

	PlaceDepositCommand(DepositRepository depositRepository, UserRepository userRepository) {
		this.depositRepository = depositRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void execute(Request request) {
		User user = userRepository.get(request.getUserId());
		Deposit deposit = new Deposit(user, request.getEuros());
		depositRepository.save(deposit);
	}
}
