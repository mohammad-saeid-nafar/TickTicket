package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.EventSchedule;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface EventScheduleRepository extends CrudRepository<EventSchedule, UUID> {
    EventSchedule findEventScheduleById(UUID id);
}