package org.pureacc.betcentral.infra.jpa.repository;

import java.util.Optional;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.infra.jpa.dao.UserJpaDao;
import org.pureacc.betcentral.infra.jpa.mapping.ToUser;
import org.pureacc.betcentral.infra.jpa.mapping.ToUserEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
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
		return ToUser.map(userEntity);
	}

	@Override
	public User get(Username username) {
		UserEntity userEntity = userJpaDao.findByUsername(username.getValue());
		return ToUser.map(userEntity);
	}

	@Override
	public Optional<User> find(Username username) {
		UserEntity userEntity = userJpaDao.findByUsername(username.getValue());
		return Optional.ofNullable(userEntity)
				.map(ToUser::map);
	}

	@Override
	public User save(User user) {
		UserEntity userEntity = ToUserEntity.map(user);
		return ToUser.map(userJpaDao.save(userEntity));
	}
}
