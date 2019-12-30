package org.pureacc.betcentral.infra.time;

import java.util.Date;

import org.pureacc.betcentral.domain.service.DomainTime;
import org.pureacc.betcentral.domain.service.Time;
import org.springframework.stereotype.Component;

@Component
class TimeService implements Time {
	TimeService() {
		DomainTime.setTime(this);
	}

	@Override
	public Date now() {
		return new Date();
	}
}
