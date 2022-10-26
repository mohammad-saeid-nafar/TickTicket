package tickticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Event;
import tickticket.model.EventType;
import tickticket.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findEventsByOrganizer(User organizer);
    List<Event> findEventsByEventTypesIn(List<EventType> eventTypes);
    List<Event> findEventsByName(String name);
}