package org.pureacc.betcentral.infra.jpa.repository;

import org.pureacc.betcentral.domain.model.Deposit;
import org.pureacc.betcentral.domain.repository.DepositRepository;
import org.pureacc.betcentral.infra.jpa.dao.DepositJpaDao;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.mapping.ToDeposit;
import org.pureacc.betcentral.infra.jpa.mapping.ToDepositEntity;
import org.pureacc.betcentral.infra.jpa.model.DepositEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
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
        return depositJpaDao.findByUser(userEntity)
                .stream()
                .map(ToDeposit::map)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Deposit deposit) {
        DepositEntity depositEntity = ToDepositEntity.map(deposit);
        depositJpaDao.save(depositEntity);
    }
}
