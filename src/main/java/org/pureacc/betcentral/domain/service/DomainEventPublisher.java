package org.pureacc.betcentral.domain.service;

import org.pureacc.betcentral.domain.events.Event;

public class DomainEventPublisher {
	private static EventPublisher eventPublisher;

	public static void setPublisher(EventPublisher eventPublisher) {
		DomainEventPublisher.eventPublisher = eventPublisher;
	}

	public static void publish(Event event) {
		eventPublisher.publish(event);
	}
}
