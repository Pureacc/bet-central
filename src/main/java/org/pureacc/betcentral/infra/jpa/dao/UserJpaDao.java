package org.pureacc.betcentral.infra.jpa.dao;

import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaDao extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
