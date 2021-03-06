package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.WinBet;
import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.vocabulary.annotation.Command;

@Command
class WinBetCommand implements WinBet {
	private final BetRepository betRepository;

	WinBetCommand(BetRepository betRepository) {
		this.betRepository = betRepository;
	}

	@Override
	public void execute(Request request) {
		Bet bet = betRepository.get(request.getBetId());
		bet.win();
		betRepository.save(bet);
	}
}
