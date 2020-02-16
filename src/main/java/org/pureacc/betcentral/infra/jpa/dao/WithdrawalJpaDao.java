package org.pureacc.betcentral.infra.jpa.dao;

import java.util.List;

import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.infra.jpa.model.WithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalJpaDao extends JpaRepository<WithdrawalEntity, Long> {
	List<WithdrawalEntity> findByUser(UserEntity userEntity);
}
