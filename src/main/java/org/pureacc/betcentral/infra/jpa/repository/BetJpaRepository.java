package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.infra.jpa.dao.BetJpaDao;
import org.pureacc.betcentral.infra.jpa.mapping.ToBet;
import org.pureacc.betcentral.infra.jpa.mapping.ToBetEntity;
import org.pureacc.betcentral.infra.jpa.model.BetEntity;
import org.pureacc.betcentral.vocabulary.BetId;
import org.springframework.stereotype.Component;

@Component
class BetJpaRepository implements BetRepository {
    private final BetJpaDao betJpaDao;

    BetJpaRepository(BetJpaDao betJpaDao) {
        this.betJpaDao = betJpaDao;
    }

    @Override
    public Bet get(BetId betId) {
        BetEntity betEntity = betJpaDao.getOne(betId.getValue());
        return ToBet.map(betEntity);
    }

    @Override
    public Bet save(Bet bet) {
        BetEntity betEntity = ToBetEntity.map(bet);
        return ToBet.map(betJpaDao.save(betEntity));
    }
}
