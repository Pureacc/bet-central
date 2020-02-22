package org.pureacc.betcentral.infra.jpa.model;

import java.time.Instant;

import javax.persistence.*;

import org.pureacc.betcentral.vocabulary.BetStatus;

@Entity
@Table(name = "bet")
public class BetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private UserEntity user;
	private double odds;
	private double euros;
	private Instant placedDate;
	private Instant resolveDate;
	@Enumerated(EnumType.STRING)
	private BetStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public double getOdds() {
		return odds;
	}

	public void setOdds(double odds) {
		this.odds = odds;
	}

	public double getEuros() {
		return euros;
	}

	public void setEuros(double euros) {
		this.euros = euros;
	}

	public Instant getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(Instant placedDate) {
		this.placedDate = placedDate;
	}

	public Instant getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(Instant resolveDate) {
		this.resolveDate = resolveDate;
	}

	public BetStatus getStatus() {
		return status;
	}

	public void setStatus(BetStatus status) {
		this.status = status;
	}
}
