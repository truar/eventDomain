package com.truar.eventdomain.eventdomain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventPublisher;
import com.truar.eventdomain.eventdomain.domain.eventstore.EventSubscriber;
import com.truar.eventdomain.eventdomain.domain.meeting.ScheduledMeeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class EventDomainApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    int eventCount;

    @BeforeEach
    void setUp() {
        eventCount = 0;
    }

    @Test
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

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(scheduleMeetingJson, headers);
        ResponseEntity<Void> voidResponseEntity = testRestTemplate.postForEntity("/meeting/schedule", entity, Void.class);

        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI uri = voidResponseEntity.getHeaders().getLocation();

        assertThat(eventCount).isEqualTo(1);

        String response = testRestTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        assertThat(root.at("/name").asText()).isEqualTo("a name");
    }

    @Test
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

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(scheduleMeetingJson, headers);
        ResponseEntity<Void> voidResponseEntity = testRestTemplate.postForEntity("/meeting/schedule", entity, Void.class);

        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI uri = voidResponseEntity.getHeaders().getLocation();

        assertThat(eventCount).isEqualTo(1);

        String response = testRestTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        assertThat(root.at("/name").asText()).isEqualTo("a name");
    }
}
