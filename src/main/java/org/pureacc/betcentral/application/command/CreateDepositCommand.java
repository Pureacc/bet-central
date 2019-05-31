package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.CreateDeposit;
import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.DepositRepository;
import org.pureacc.betcentral.domain.repository.UserRepository;

import javax.transaction.Transactional;

@Command
class CreateDepositCommand implements CreateDeposit {
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;

    CreateDepositCommand(DepositRepository depositRepository, UserRepository userRepository) {
        this.depositRepository = depositRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void execute(Request request) {
        User user = userRepository.get(request.getUserId());
        Deposit deposit = new Deposit(user, request.getEuros());
        depositRepository.save(deposit);
    }
}
