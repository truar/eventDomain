package com.truar.eventdomain.eventdomain.application;

import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventSubscriber;
import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MeetingRoomApplicationService {

    public MeetingRoomApplicationService() {
        EventPublisher.instance()
                .subscribe(new EventSubscriber<ScheduledMeeting>() {
                    @Override
                    public void handleEvent(ScheduledMeeting event) {
                        handleScheduledMeeting(event);
                    }

                    @Override
                    public Class<ScheduledMeeting> subscribedEventType() {
                        return ScheduledMeeting.class;
                    }
                });

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleScheduledMeeting(ScheduledMeeting event) {
        System.out.println(event);
        throw new RuntimeException("should not rollback Meeting");
    }
}
