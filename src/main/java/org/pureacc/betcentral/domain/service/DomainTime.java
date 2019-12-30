package org.pureacc.betcentral.domain.service;

import java.util.Date;

public class DomainTime {
	private static Time time;

	public static void setTime(Time time) {
		DomainTime.time = time;
	}

	public static Date now() {
		return time.now();
	}
}
