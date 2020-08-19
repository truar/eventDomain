package com.truar.eventdomain.eventdomain.configuration;

import com.truar.eventdomain.eventdomain.application.CalendarApplicationService;
import com.truar.eventdomain.eventdomain.application.MeetingRoomApplicationService;
import com.truar.eventdomain.eventdomain.domain.meeting.MeetingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CalendarApplicationService calendarApplicationService(MeetingRepository meetingRepository) {
        return new CalendarApplicationService(meetingRepository);
    }

    @Bean
    public MeetingRoomApplicationService meetingRoomApplicationService() {
        return new MeetingRoomApplicationService();
    }
}
