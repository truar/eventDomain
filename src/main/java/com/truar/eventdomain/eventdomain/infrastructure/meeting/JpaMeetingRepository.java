package com.truar.eventdomain.eventdomain.infrastructure.meeting;

import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import com.truar.eventdomain.eventdomain.domain.meeting.MeetingRepository;
import org.springframework.stereotype.Repository;

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
}
