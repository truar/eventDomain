package com.truar.eventdomain.eventdomain.domain.meeting;

public interface MeetingRepository {
    void scheduleMeeting(Meeting meeting);

    String nextId();

    Meeting findById(String meetingId);
}
