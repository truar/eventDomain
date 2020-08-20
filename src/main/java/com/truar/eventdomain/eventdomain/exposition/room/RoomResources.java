package com.truar.eventdomain.eventdomain.exposition.room;

import com.truar.eventdomain.eventdomain.application.MeetingRoomApplicationService;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventSubscriber;
import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.springframework.stereotype.Component;

@Component
public class RoomResources {

    private MeetingRoomApplicationService meetingRoomApplicationService;

    public RoomResources(MeetingRoomApplicationService meetingRoomApplicationService) {
        this.meetingRoomApplicationService = meetingRoomApplicationService;

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

    private void handleScheduledMeeting(ScheduledMeeting event) {
        meetingRoomApplicationService.bookRoom(event);
    }
}
