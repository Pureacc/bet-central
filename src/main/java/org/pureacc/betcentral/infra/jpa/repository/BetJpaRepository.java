package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.Bet;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.BetRepository;
import org.pureacc.betcentral.infra.jpa.dao.BetJpaDao;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;
import org.pureacc.betcentral.infra.jpa.model.BetEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.springframework.stereotype.Component;

@Component
class BetJpaRepository implements BetRepository {
    private final BetJpaDao betJpaDao;
    private final UserJpaDao userJpaDao;

    BetJpaRepository(BetJpaDao betJpaDao, UserJpaDao userJpaDao) {
        this.betJpaDao = betJpaDao;
        this.userJpaDao = userJpaDao;
    }

    @Override
    public Bet get(BetId betId) {
        BetEntity betEntity = betJpaDao.getOne(betId.getValue());
        return map(betEntity);
    }

    @Override
    public Bet save(Bet bet) {
        BetEntity betEntity = map(bet);
        return map(betJpaDao.save(betEntity));
    }

    private BetEntity map(Bet bet) {
        BetEntity betEntity = new BetEntity();
        if (bet.getId() != null) {
            betEntity.setId(bet.getId().getValue());
        }
        UserEntity userEntity = userJpaDao.getOne(bet.getUser().getId().getValue());
        betEntity.setUser(userEntity);
        betEntity.setOdds(bet.getOdds().getOdds());
        betEntity.setEuros(bet.getEuros().getValue());
        betEntity.setPlacedDate(bet.getPlacedDate());
        betEntity.setResolveDate(bet.getResolveDate());
        betEntity.setStatus(bet.getStatus());
        return betEntity;
    }

    private Bet map(BetEntity betEntity) {
        User user = map(betEntity.getUser());
        return new Bet(BetId.of(betEntity.getId()), user, DecimalOdds.of(betEntity.getOdds()), Euros.of(betEntity.getEuros()), betEntity.getPlacedDate(), betEntity.getResolveDate(), betEntity.getStatus());
    }

    private User map(UserEntity userEntity) {
        return new User(new UserId(userEntity.getId()), userEntity.getUsername(), map(userEntity.getBalance()));
    }

    private Balance map(BalanceEntity balanceEntity) {
        return new Balance(Euros.of(balanceEntity.getEuros()));
    }
}
