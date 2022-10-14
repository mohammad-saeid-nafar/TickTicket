package dao;

import model.EventSchedule;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface EventScheduleRepository extends CrudRepository<EventSchedule, UUID> {
}