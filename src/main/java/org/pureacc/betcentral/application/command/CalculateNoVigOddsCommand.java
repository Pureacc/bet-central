package org.pureacc.betcentral.application.command;

import org.pureacc.betcentral.application.api.CalculateNoVigOdds;
import org.pureacc.betcentral.domain.model.BetOffer;
import org.springframework.stereotype.Component;

@Component
class CalculateNoVigOddsCommand implements CalculateNoVigOdds {
    @Override
    public Response execute(Request request) {
        BetOffer betOffer = new BetOffer(request.getOddsA(), request.getOddsB());
        return Response.newBuilder()
                .withVig(betOffer.getVig().getPercentage())
                .withNoVigOddsA(betOffer.getNoVigOddsA())
                .withNoVigOddsB(betOffer.getNoVigOddsB())
                .build();
    }
}
