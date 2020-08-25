package com.truar.eventdomain.eventdomain.exposition.meeting;


import com.truar.eventdomain.eventdomain.application.CalendarApplicationService;
import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CalendarResources {

    private CalendarApplicationService calendarApplicationService;

    public CalendarResources(CalendarApplicationService calendarApplicationService) {
        this.calendarApplicationService = calendarApplicationService;
    }

    @PostMapping("/meeting/schedule")
    public ResponseEntity<Void> createEvent(@RequestBody CreateMeetingDTO createMeetingDTO) {
        String meetingId = calendarApplicationService.scheduleMeeting(createMeetingDTO.name, createMeetingDTO.occuredOn, createMeetingDTO.duration);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/meeting/{id}")
                .buildAndExpand(meetingId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/meeting/{meetingId}")
    public MeetingDTO getMeetingDTO(@PathVariable String meetingId) {
        return toMeetingDTO(calendarApplicationService.meetingOfId(meetingId));
    }

    private MeetingDTO toMeetingDTO(Meeting meetingOfId) {
        return new MeetingDTO(meetingOfId.getName());
    }
}
