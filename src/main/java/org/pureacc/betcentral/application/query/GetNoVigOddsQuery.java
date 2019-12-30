package org.pureacc.betcentral.application.query;

import org.pureacc.betcentral.application.api.GetNoVigOdds;
import org.pureacc.betcentral.domain.model.BetOffer;
import org.pureacc.betcentral.vocabulary.annotation.Query;

@Query
class GetNoVigOddsQuery implements GetNoVigOdds {
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
