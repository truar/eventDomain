package com.truar.eventdomain.eventdomain.domain.meeting;

import java.time.LocalDateTime;

public class ScheduledMeeting {
    private final String name;
    private final LocalDateTime occuredOn;
    private final int duration;

    public ScheduledMeeting(String name, LocalDateTime occuredOn, int duration) {
        this.name = name;
        this.occuredOn = occuredOn;
        this.duration = duration;
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

    @Override
    public String toString() {
        return "ScheduledMeeting{" +
                "name='" + name + '\'' +
                ", occuredOn=" + occuredOn +
                ", duration=" + duration +
                '}';
    }
}
