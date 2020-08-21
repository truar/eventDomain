package com.truar.eventdomain.eventdomain;

import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventSubscriber;
import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
class EventDomainApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    int eventCount;

    @BeforeEach
    void setUp() {
        testEntityManager.clear();
        eventCount = 0;
    }

    @Test
    @Transactional
    void test_with_success() throws Exception {

        EventPublisher.instance()
                .subscribe(new EventSubscriber<ScheduledMeeting>() {
                    @Override
                    public void handleEvent(ScheduledMeeting tickedEvent) {
                        eventCount++;
                    }

                    @Override
                    public Class<ScheduledMeeting> subscribedEventType() {
                        return ScheduledMeeting.class;
                    }
                });

        String scheduleMeetingJson = "{" +
                "\"name\":\"a name\"," +
                "\"occuredOn\":\"2020-08-18T10:00:00\"," +
                "\"duration\":1" +
                "}";

        mockMvc.perform(post("/meeting/schedule")
                .content(scheduleMeetingJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(eventCount).isEqualTo(1);

        Meeting meeting = Optional.ofNullable(testEntityManager.find(Meeting.class, 1l)).orElseGet(() -> testEntityManager.find(Meeting.class, 2l));
        assertNotNull(meeting);
        assertThat(meeting.getName()).isEqualTo("a name");
    }

    @Test
    @Transactional
    void test_with_exception() throws Exception {

        EventPublisher.instance()
                .subscribe(new EventSubscriber<ScheduledMeeting>() {
                    @Override
                    public void handleEvent(ScheduledMeeting tickedEvent) {
                        System.out.println(Thread.currentThread().getName());
                        eventCount++;
                    }

                    @Override
                    public Class<ScheduledMeeting> subscribedEventType() {
                        return ScheduledMeeting.class;
                    }
                });

        String scheduleMeetingJson = "{" +
                "\"name\":\"a name\"," +
                "\"occuredOn\":\"2020-08-18T10:00:00\"," +
                "\"duration\":0" +
                "}";

        mockMvc.perform(post("/meeting/schedule")
                .content(scheduleMeetingJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(eventCount).isEqualTo(1);

        Meeting meeting = testEntityManager.find(Meeting.class, 1l);
        assertNotNull(meeting);
        assertThat(meeting.getName()).isEqualTo("a name");
    }
}
