package com.truar.eventdomain.eventdomain.domain.eventstore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventPublisher {

    private static EventPublisher instance = new EventPublisher();
    private final List<EventSubscriber> subscribers = Collections.synchronizedList(new ArrayList<>());

    private EventPublisher() {
    }

    public static EventPublisher instance() {
        return instance;
    }

    public <T> void publish(T event) {
        subscribers.parallelStream()
                .filter(s -> s.subscribedEventType() == event.getClass())
                .forEach(s -> {
                    try {
                        s.handleEvent(event);
                    } catch (RuntimeException exception) {
                        exception.printStackTrace();
                    }
                });
    }

    public <T> void subscribe(EventSubscriber<T> eventSubscriber) {
        subscribers.add(eventSubscriber);
    }
}
