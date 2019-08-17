package org.pureacc.betcentral.infra.jpa.dao;

import org.pureacc.betcentral.infra.jpa.model.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetJpaDao extends JpaRepository<BetEntity, Long> {
}
