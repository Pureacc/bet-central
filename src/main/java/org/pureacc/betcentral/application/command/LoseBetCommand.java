package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.LoseBet;
import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.repository.BetRepository;

@Command
class LoseBetCommand implements LoseBet {
    private final BetRepository betRepository;

    LoseBetCommand(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public void execute(Request request) {
        Bet bet = betRepository.get(request.getBetId());
        bet.lose();
        betRepository.save(bet);
    }
}
