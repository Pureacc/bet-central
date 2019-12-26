package org.pureacc.betcentral.infra.events.controller;

import org.pureacc.betcentral.TestException;
import org.pureacc.betcentral.domain.events.Event;
import org.pureacc.betcentral.infra.security.application.checks.HasAuthority;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class TestEventController {
	@EventListener
	public void handle(TestEvent testEvent) {
		SecurityContextHolder.getContext()
				.getAuthentication()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.filter(a -> a.equals(HasAuthority.Authority.SYSTEM.name()))
				.findAny()
				.orElseThrow(() -> new TestException("event listener was not called with the SYSTEM authority"));
		testEvent.handled = true;
	}

	public static final class TestEvent implements Event {
		public boolean handled = false;
	}
}
