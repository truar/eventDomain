package com.truar.eventdomain.eventdomain.infrastructure.meeting;

import com.truar.eventdomain.eventdomain.domain.meeting.Meeting;
import org.springframework.data.repository.CrudRepository;

public interface JpaMeetingDAO extends CrudRepository<Meeting, String> {
}
