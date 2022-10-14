package dao;

import model.EventType;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface EventTypeRepository extends CrudRepository<EventType, UUID> {
}