package tickticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findEventsByOrganizer(User organizer);

    // TODO check if this works, you might be looking for the one right under which gets all the events with a certain event-type
    List<Event> findEventsByEventTypesIn(List<EventType> eventTypes);
    List<Event> findEventsByEventTypesContains(EventType eventType);
    List<Event> findEventsByName(String name);
    List<Event> findEventsByCostBetween(double minCost, double maxCost);
    List<Event> findEventsByCapacityBetween(int minCapacity, int maxCapacity);
    List<Event> findEventsByEventScheduleId(UUID uuid);
    List<Event> findEventsByAddress(String address);




}