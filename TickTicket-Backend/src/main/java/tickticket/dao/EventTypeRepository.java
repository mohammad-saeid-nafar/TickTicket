package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.EventType;

import java.util.UUID;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, UUID> {
    boolean existsEventTypeByName(String name);
    EventType findEventTypeByName(String name);
}