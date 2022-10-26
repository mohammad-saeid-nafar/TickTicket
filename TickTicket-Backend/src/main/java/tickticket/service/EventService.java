package tickticket.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import tickticket.dao.EventRepository;
import tickticket.model.EventSchedule;
import tickticket.model.User;
import tickticket.model.Event;
import tickticket.model.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

	@Autowired
	private EventRepository eventRepository;


	public Event createEvent(String name, String description, Integer capacity, Double cost, String address,
							 String email, String phoneNumber, User organizer, List<EventType> eventTypes,
							 LocalDateTime start, LocalDateTime end) {

		if(name == null || name.equals("")) throw new IllegalArgumentException("Name cannot be blank");

        if(address == null || address.equals("")) throw new IllegalArgumentException("Address cannot be blank");

		if(email == null || email.equals("")) throw new IllegalArgumentException("Email cannot be blank");

		if(phoneNumber == null || phoneNumber.equals("")) throw new IllegalArgumentException("Phone number cannot be blank");

        if(capacity == null || capacity == 0) throw new IllegalArgumentException("Capacity cannot be blank or 0");

        if(cost == null ) throw new IllegalArgumentException("Cost cannot be blank");

		if(start==null) throw new IllegalArgumentException("Start of event schedule cannot be blank");

		if(end==null) throw new IllegalArgumentException("End of event schedule cannot be blank");

		EventSchedule schedule = new EventSchedule();
		schedule.setStartDateTime(start);
		schedule.setEndDateTime(end);

        Event newEvent = new Event();
        newEvent.setName(name);
        newEvent.setAddress(address);
        newEvent.setDescription(description);
        newEvent.setCapacity(capacity);
        newEvent.setCost(cost);
        newEvent.setEmail(email);
        newEvent.setPhoneNumber(phoneNumber);
        newEvent.setOrganizer(organizer);
        newEvent.setEventTypes(eventTypes);
		newEvent.setEventSchedule(schedule);

		eventRepository.save(newEvent);

		return newEvent;
	}

	public Event updateEvent(UUID id, String name, String description, Integer capacity, Double cost, String address,
							 String email, String phoneNumber, User organizer, List<EventType> eventTypes) {

		Event event = getEventById(id);

        if (event == null){
            throw new IllegalArgumentException("Event not found");
        }

		if (name != null || !name.equals("") && !name.equals(event.getName())){
			event.setName(name);
		}

		if(organizer != null && !organizer.getId().equals(event.getOrganizer().getId())){
			event.setOrganizer(organizer);
		}

		if(eventTypes != null){
			event.setEventTypes(eventTypes);
		}
		
		if(description != null && !description.equals("") && !description.equals(event.getDescription())) {
			event.setDescription(description);
		}

		if(capacity != null && !capacity.equals(0) && capacity != event.getCapacity()) {
			event.setCapacity(capacity);
		}

        if(cost != null && cost != event.getCost()) {
			event.setCost(cost);
		}

		if(address != null && !address.equals("") && !address.equals(event.getAddress())) {
			event.setAddress(address);
		}

        if(email != null && !email.equals("") && !email.equals(event.getEmail())) {
			event.setEmail(email);
		}

		if(phoneNumber != null && !phoneNumber.equals("") && !phoneNumber.equals(event.getPhoneNumber())) {
			event.setPhoneNumber(phoneNumber);
		}

		eventRepository.save(event);

		return event;
	}


	public Event getEventById(UUID id) {
		return eventRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Event " + id + " not found"));
	}

	public boolean deleteEventById(UUID id) {
		Event event = getEventById(id);
		eventRepository.delete(event);
		return true;
	}

    public List<Event> getEventsByName(String name){
        return eventRepository.findEventsByName(name);
    }

	public List<Event> getAllEventsFromType(List<EventType> eventTypes) {
		return eventRepository.findEventsByEventTypesIn(eventTypes);
    }

	public List<Event> getAllEventsFromOrganizer(User organizer) {
		return eventRepository.findEventsByOrganizer(organizer);

	}

	public List<Event> getAllEvents(){
		return toList(eventRepository.findAll());
	}

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}
