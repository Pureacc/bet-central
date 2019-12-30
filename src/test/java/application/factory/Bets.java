package application.factory;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.vocabulary.BetStatus;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;

public class Bets {
	private final BetRepository betRepository;

	public Bets(BetRepository betRepository) {
		this.betRepository = betRepository;
	}

	public Bet aBet(User user, Euros euros, BetStatus status) {
		switch (status) {
		case PENDING:
			return aPendingBet(user, euros);
		case WON:
			return aWonBet(user, euros);
		case LOST:
			return aLostBet(user, euros);
		default:
			throw new IllegalStateException("Unexpected value: " + status);
		}
	}

	public Bet aPendingBet(User user, Euros euros) {
		Bet bet = create(user, euros);
		return save(bet);
	}

	public Bet aWonBet(User user, Euros euros) {
		Bet bet = create(user, euros);
		bet.win();
		return save(bet);
	}

	public Bet aLostBet(User user, Euros euros) {
		Bet bet = create(user, euros);
		bet.lose();
		return save(bet);
	}

	private Bet create(User user, Euros euros) {
		return new Bet(user, DecimalOdds.of(2.0), euros);
	}

	private Bet save(Bet bet) {
		return betRepository.save(bet);
	}
}
