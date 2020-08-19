package com.truar.eventdomain.eventdomain.domain.meeting;

import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDateTime occuredOn;
    private int duration;

    protected Meeting() {
    }

    public Meeting(String name, LocalDateTime occuredOn, int duration) {
        this.name = name;
        this.occuredOn = occuredOn;
        this.duration = duration;

        EventPublisher.instance()
                .publish(new ScheduledMeeting(name, occuredOn, duration));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getOccuredOn() {
        return occuredOn;
    }

    public int getDuration() {
        return duration;
    }
}
