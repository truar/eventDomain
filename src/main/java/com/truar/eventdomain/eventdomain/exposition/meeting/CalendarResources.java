package com.truar.eventdomain.eventdomain.exposition.meeting;

import com.truar.eventdomain.eventdomain.application.CalendarApplicationService;
import com.truar.eventdomain.eventdomain.application.UnexpectedMeetingRoomServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CalendarResources {

    private CalendarApplicationService calendarApplicationService;

    public CalendarResources(CalendarApplicationService calendarApplicationService) {
        this.calendarApplicationService = calendarApplicationService;
    }

    @PostMapping("/meeting/schedule")
    public void createEvent(@RequestBody CreateMeetingDTO createMeetingDTO) {
        try {
            calendarApplicationService.scheduleMeeting(createMeetingDTO.name, createMeetingDTO.occuredOn, createMeetingDTO.duration);
        } catch (UnexpectedMeetingRoomServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create a neeting, please try again later", e);
        }
    }
}
