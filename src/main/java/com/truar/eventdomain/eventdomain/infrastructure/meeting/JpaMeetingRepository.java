package com.truar.eventdomain.eventdomain.infrastructure.meeting;

import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;
import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import com.truar.eventdomain.eventdomain.domain.meeting.MeetingRepository;
import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaMeetingRepository implements MeetingRepository {
    private JpaMeetingDAO jpaMeetingDAO;

    public JpaMeetingRepository(JpaMeetingDAO jpaMeetingDAO) {
        this.jpaMeetingDAO = jpaMeetingDAO;
    }

    @Override
    public void scheduleMeeting(Meeting meeting) {
        jpaMeetingDAO.save(meeting);
    }

    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Meeting findById(String meetingId) {
        return jpaMeetingDAO.findById(meetingId)
                .orElseThrow();
    }
}
