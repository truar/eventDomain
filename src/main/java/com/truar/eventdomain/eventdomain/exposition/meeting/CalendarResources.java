package com.truar.eventdomain.eventdomain.exposition.meeting;

import com.truar.eventdomain.eventdomain.application.CalendarApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
    }
}
