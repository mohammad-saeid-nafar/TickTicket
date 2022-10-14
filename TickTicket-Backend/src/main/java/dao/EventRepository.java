package dao;

import model.Event;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, UUID> {
}