package com.truar.eventdomain.eventdomain.domain.eventstore;

public interface EventSubscriber<T> {
    void handleEvent(T tickedEvent);

    Class<T> subscribedEventType();
}
