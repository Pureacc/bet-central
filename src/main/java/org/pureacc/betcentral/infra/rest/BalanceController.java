package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.CreateDeposit;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class BalanceController {
	private final CreateDeposit createDeposit;

	BalanceController(CreateDeposit createDeposit) {
		this.createDeposit = createDeposit;
	}

	@PostMapping("api/balance/deposit")
	public void deposit(@RequestBody DepositWebRequest webRequest) {
		CreateDeposit.Request request = CreateDeposit.Request.newBuilder()
				.withUserId(webRequest.getUserId())
				.withEuros(webRequest.getEuros())
				.build();
		createDeposit.execute(request);
	}

	static final class DepositWebRequest {
		private final long userId;
		private final double euros;

		@JsonCreator
		DepositWebRequest(@JsonProperty("userId") long userId, @JsonProperty("euros") double euros) {
			this.userId = userId;
			this.euros = euros;
		}

		public UserId getUserId() {
			return UserId.of(userId);
		}

		public Euros getEuros() {
			return Euros.of(euros);
		}
	}
}
