package org.pureacc.betcentral.application.mock;

import org.pureacc.betcentral.domain.events.Event;
import org.pureacc.betcentral.domain.service.EventPublisher;

import java.util.LinkedList;
import java.util.Queue;

public class TestEventPublisher implements EventPublisher {
    private final Queue<Event> events = new LinkedList<>();

    @Override
    public void publish(Event event) {
        events.add(event);
    }

    public Event poll() {
        return events.poll();
    }
}
