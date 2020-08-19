package com.truar.eventdomain.eventdomain.domain.eventstore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventPublisher {

    static EventPublisher instance = new EventPublisher();
    private final List<EventSubscriber> subscribers = Collections.synchronizedList(new ArrayList<>());

    private EventPublisher() {
    }

    public static EventPublisher instance() {
        return instance;
    }

    public <T> void publish(T event) {
        subscribers.stream()
                .filter(s -> s.subscribedEventType() == event.getClass())
                .forEach(s -> s.handleEvent(event));
    }

    public <T> void subscribe(EventSubscriber<T> eventSubscriber) {
        subscribers.add(eventSubscriber);
    }
}
