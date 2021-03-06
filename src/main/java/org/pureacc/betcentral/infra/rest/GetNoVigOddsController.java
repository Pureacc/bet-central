package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.GetNoVigOdds;
import org.pureacc.betcentral.application.api.GetNoVigOdds.Request;
import org.pureacc.betcentral.application.api.GetNoVigOdds.Response;
import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetNoVigOddsController {
	private final GetNoVigOdds getNoVigOdds;

	GetNoVigOddsController(GetNoVigOdds getNoVigOdds) {
		this.getNoVigOdds = getNoVigOdds;
	}

	@GetMapping("/api/calculate/novig")
	protected WebResponse get(@RequestParam double oddsA, @RequestParam double oddsB) {
		Request request = Request.newBuilder()
				.withOddsA(DecimalOdds.of(oddsA))
				.withOddsB(DecimalOdds.of(oddsB))
				.build();
		Response response = getNoVigOdds.execute(request);
		return new WebResponse(response);
	}

	private static final class WebResponse {
		private final double vigPercentage;
		private final double noVigOddsA;
		private final double noVigOddsB;

		WebResponse(Response response) {
			this.vigPercentage = response.getVig()
					.getValue();
			this.noVigOddsA = response.getNoVigOddsA()
					.getValue();
			this.noVigOddsB = response.getNoVigOddsB()
					.getValue();
		}

		public double getVigPercentage() {
			return vigPercentage;
		}

		public double getNoVigOddsA() {
			return noVigOddsA;
		}

		public double getNoVigOddsB() {
			return noVigOddsB;
		}
	}
}
