package org.pureacc.betcentral.infra.jpa.dao;

import java.util.List;

import org.pureacc.betcentral.infra.jpa.model.DepositEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositJpaDao extends JpaRepository<DepositEntity, Long> {
	List<DepositEntity> findByUser(UserEntity userEntity);
}
