package com.truar.eventdomain.eventdomain.application;

import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import com.truar.eventdomain.eventdomain.domain.meeting.MeetingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class CalendarApplicationService {
    private MeetingRepository meetingRepository;

    public CalendarApplicationService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public String scheduleMeeting(String name, LocalDateTime occuredOn, int duration) {
        String id = meetingRepository.nextId();
        meetingRepository.scheduleMeeting(new Meeting(id, name, occuredOn, duration));
        return id;
    }

    public Meeting meetingOfId(String meetingId) {
        return meetingRepository.findById(meetingId);
    }
}
