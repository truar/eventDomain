package com.truar.eventdomain.eventdomain.application;

import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MeetingRoomApplicationService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void bookRoom(ScheduledMeeting event) {
        System.out.println(Thread.currentThread().getName() + " - " + event);

        if(event.getDuration() < 1) {
            throw new UnexpectedMeetingRoomServiceException("should not rollback Meeting");
        }
    }
}
