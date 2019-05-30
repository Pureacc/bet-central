package org.pureacc.betcentral.infra.jpa.dao;

import org.pureacc.betcentral.infra.jpa.model.DepositEntity;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositJpaDao extends JpaRepository<DepositEntity, Long> {
    List<DepositEntity> findByUser(UserEntity userEntity);
}
