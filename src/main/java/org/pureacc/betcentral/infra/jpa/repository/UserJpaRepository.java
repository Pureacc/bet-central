package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;
import org.springframework.stereotype.Component;

@Component
class UserJpaRepository implements UserRepository {
    private final UserJpaDao userJpaDao;

    UserJpaRepository(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public User get(UserId userId) {
        UserEntity userEntity = userJpaDao.getOne(userId.getValue());
        return map(userEntity);
    }

    @Override
    public User get(Username username) {
        UserEntity userEntity = userJpaDao.findByUsername(username.getValue());
        return map(userEntity);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = map(user);
        return map(userJpaDao.save(userEntity));
    }

    private User map(UserEntity userEntity) {
        return new User(new UserId(userEntity.getId()), userEntity.getUsername(), map(userEntity.getBalance()));
    }

    private Balance map(BalanceEntity balanceEntity) {
        return new Balance(Euros.of(balanceEntity.getEuros()));
    }

    private UserEntity map(User user) {
        UserEntity userEntity = new UserEntity();
        if (user.getId() != null) {
            userEntity.setId(user.getId().getValue());
        }
        userEntity.setUsername(user.getUsername());
        userEntity.setBalance(map(user.getBalance()));
        return userEntity;
    }

    private BalanceEntity map(Balance balance) {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setEuros(balance.getEuros().getValue());
        return balanceEntity;
    }
}
