package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.model.BalanceEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
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
    public User get(String username) {
        UserEntity userEntity = userJpaDao.findByUsername(username);
        return map(userEntity);
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = map(user);
        userJpaDao.save(userEntity);
    }

    private User map(UserEntity userEntity) {
        return new User(new UserId(userEntity.getId()), userEntity.getUsername(), map(userEntity.getBalance()));
    }

    private Balance map(BalanceEntity balanceEntity) {
        return new Balance(new Euros(balanceEntity.getEuros()));
    }

    private UserEntity map(User user) {
        UserEntity userEntity = new UserEntity();
        if (user.getUserId() != null) {
            userEntity.setId(user.getUserId().getValue());
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
