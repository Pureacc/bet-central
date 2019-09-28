package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.PlaceBet;
import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.domain.repository.UserRepository;

@Command
class PlaceBetCommand implements PlaceBet {
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    PlaceBetCommand(BetRepository betRepository, UserRepository userRepository) {
        this.betRepository = betRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        User user = userRepository.get(request.getUserId());
        Bet bet = new Bet(user, request.getOdds(), request.getEuros());
        Bet savedBet = betRepository.save(bet);
        return Response.newBuilder().withBetId(savedBet.getId()).build();
    }
}
