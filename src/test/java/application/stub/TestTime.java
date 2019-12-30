package application.stub;

import java.util.Date;

import org.pureacc.betcentral.domain.service.Time;

public class TestTime implements Time {
	private static final Date NOW = new Date();

	@Override
	public Date now() {
		return NOW;
	}
}
