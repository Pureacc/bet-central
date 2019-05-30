package org.pureacc.betcentral.domain.service;

import org.pureacc.betcentral.domain.events.Event;

public interface EventPublisher {
    void publish(Event event);
}
