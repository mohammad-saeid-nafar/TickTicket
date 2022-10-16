package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Event;

import java.util.UUID;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {
}