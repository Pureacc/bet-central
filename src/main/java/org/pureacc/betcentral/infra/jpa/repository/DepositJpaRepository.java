package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.DepositRepository;
import org.pureacc.betcentral.infra.jpa.dao.DepositJpaDao;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;
import org.pureacc.betcentral.infra.jpa.model.DepositEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class DepositJpaRepository implements DepositRepository {
    private final DepositJpaDao depositJpaDao;
    private final UserJpaDao userJpaDao;

    DepositJpaRepository(DepositJpaDao depositJpaDao, UserJpaDao userJpaDao) {
        this.depositJpaDao = depositJpaDao;
        this.userJpaDao = userJpaDao;
    }

    @Override
    public List<Deposit> findByUser(UserId userId) {
        UserEntity userEntity = userJpaDao.getOne(userId.getValue());
        return depositJpaDao.findByUser(userEntity).stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void save(Deposit deposit) {
        DepositEntity depositEntity = map(deposit);
        depositJpaDao.save(depositEntity);
    }

    private Deposit map(DepositEntity depositEntity) {
        return new Deposit(map(depositEntity.getUser()), Euros.of(depositEntity.getEuros()), depositEntity.getDate());
    }

    private User map(UserEntity userEntity) {
        return new User(new UserId(userEntity.getId()), userEntity.getUsername(), map(userEntity.getBalance()));
    }

    private Balance map(BalanceEntity balanceEntity) {
        return new Balance(Euros.of(balanceEntity.getEuros()));
    }

    private DepositEntity map(Deposit deposit) {
        DepositEntity depositEntity = new DepositEntity();
        UserEntity userEntity = userJpaDao.getOne(deposit.getUser().getId().getValue());
        depositEntity.setUser(userEntity);
        depositEntity.setEuros(deposit.getEuros().getValue());
        depositEntity.setDate(deposit.getDate());
        return depositEntity;
    }
}
