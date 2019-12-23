package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.LoseBet;
import org.pureacc.betcentral.application.api.PlaceBet;
import org.pureacc.betcentral.application.api.WinBet;
import org.pureacc.betcentral.vocabulary.BetId;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class BetController {
	private final PlaceBet placeBet;
	private final WinBet winBet;
	private final LoseBet loseBet;

	BetController(PlaceBet placeBet, WinBet winBet, LoseBet loseBet) {
		this.placeBet = placeBet;
		this.winBet = winBet;
		this.loseBet = loseBet;
	}

	@PostMapping("/api/bet/place")
	public PlaceBetWebResponse placeBet(@RequestBody PlaceBetWebRequest webRequest) {
		PlaceBet.Request request = PlaceBet.Request.newBuilder()
				.withUserId(webRequest.getUserId())
				.withOdds(webRequest.getOdds())
				.withEuros(webRequest.getEuros())
				.build();
		PlaceBet.Response response = placeBet.execute(request);
		return new PlaceBetWebResponse(response.getBetId());
	}

	@PostMapping("/api/bet/win")
	public void winBet(@RequestBody WinBetWebRequest webRequest) {
		WinBet.Request request = WinBet.Request.newBuilder()
				.withBetId(webRequest.getBetId())
				.build();
		winBet.execute(request);
	}

	@PostMapping("/api/bet/lose")
	public void loseBet(@RequestBody LoseBetWebRequest webRequest) {
		LoseBet.Request request = LoseBet.Request.newBuilder()
				.withBetId(webRequest.getBetId())
				.build();
		loseBet.execute(request);
	}

	static final class PlaceBetWebRequest {
		private final long userId;
		private final double odds;
		private final double euros;

		@JsonCreator
		PlaceBetWebRequest(@JsonProperty("userId") long userId, @JsonProperty("odds") double odds,
				@JsonProperty("euros") double euros) {
			this.userId = userId;
			this.odds = odds;
			this.euros = euros;
		}

		public UserId getUserId() {
			return UserId.of(userId);
		}

		public DecimalOdds getOdds() {
			return DecimalOdds.of(odds);
		}

		public Euros getEuros() {
			return Euros.of(euros);
		}
	}

	static final class PlaceBetWebResponse {
		private final long betId;

		PlaceBetWebResponse(BetId betId) {
			this.betId = betId.getValue();
		}

		public long getBetId() {
			return betId;
		}
	}

	static final class WinBetWebRequest {
		private final long betId;

		@JsonCreator
		WinBetWebRequest(@JsonProperty("betId") long betId) {
			this.betId = betId;
		}

		public BetId getBetId() {
			return BetId.of(betId);
		}
	}

	static final class LoseBetWebRequest {
		private final long betId;

		@JsonCreator
		LoseBetWebRequest(@JsonProperty("betId") long betId) {
			this.betId = betId;
		}

		public BetId getBetId() {
			return BetId.of(betId);
		}
	}
}
