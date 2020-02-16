package org.pureacc.betcentral.infra.jpa.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.pureacc.betcentral.domain.model.Withdrawal;
import org.pureacc.betcentral.domain.repository.WithdrawalRepository;
import org.pureacc.betcentral.infra.jpa.dao.WithdrawalJpaDao;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.mapping.ToWithdrawal;
import org.pureacc.betcentral.infra.jpa.mapping.ToWithdrawalEntity;
import org.pureacc.betcentral.infra.jpa.model.WithdrawalEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.UserId;
import org.springframework.stereotype.Component;

@Component
class WithdrawalJpaRepository implements WithdrawalRepository {
	private final WithdrawalJpaDao withdrawalJpaDao;
	private final UserJpaDao userJpaDao;

	WithdrawalJpaRepository(WithdrawalJpaDao withdrawalJpaDao, UserJpaDao userJpaDao) {
		this.withdrawalJpaDao = withdrawalJpaDao;
		this.userJpaDao = userJpaDao;
	}

	@Override
	public List<Withdrawal> findByUser(UserId userId) {
		UserEntity userEntity = userJpaDao.getOne(userId.getValue());
		return withdrawalJpaDao.findByUser(userEntity)
				.stream()
				.map(ToWithdrawal::map)
				.collect(Collectors.toList());
	}

	@Override
	public void save(Withdrawal withdrawal) {
		WithdrawalEntity withdrawalEntity = ToWithdrawalEntity.map(withdrawal);
		withdrawalJpaDao.save(withdrawalEntity);
	}
}
