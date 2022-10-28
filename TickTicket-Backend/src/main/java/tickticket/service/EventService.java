package tickticket.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tickticket.dao.EventRepository;
import tickticket.dto.EventDTO;
import tickticket.model.EventSchedule;
import tickticket.model.User;
import tickticket.model.Event;
import tickticket.model.EventType;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class EventService {

	private EventRepository eventRepository;
	private UserService userService;
	private EventTypeService eventTypeService;
	private EventScheduleService eventScheduleService;

	public Event createEvent(EventDTO eventDTO) {
		String name = eventDTO.getName();
		String description = eventDTO.getDescription();
	    Integer capacity = eventDTO.getCapacity();
		Double cost = eventDTO.getCost();
		String address = eventDTO.getAddress();
		String email = eventDTO.getEmail();
		String phoneNumber = eventDTO.getPhoneNumber();
		UUID organizerId = eventDTO.getOrganizerId();
		UUID eventScheduleId = eventDTO.getEventScheduleId();
		List<UUID> eventTypeIds = eventDTO.getEventTypeIds();

		if(name == null || name.equals("")) throw new IllegalArgumentException("Name cannot be blank");

        if(address == null || address.equals("")) throw new IllegalArgumentException("Address cannot be blank");

		if(email == null || email.equals("")) throw new IllegalArgumentException("Email cannot be blank");

		if(phoneNumber == null || phoneNumber.equals("")) throw new IllegalArgumentException("Phone number cannot be blank");

        if(capacity == null || capacity == 0) throw new IllegalArgumentException("Capacity cannot be blank or 0");

        if(cost == null ) throw new IllegalArgumentException("Cost cannot be blank");

		Event newEvent = new Event();

		if (eventScheduleId == null) {
			LocalDateTime start = eventDTO.getStart();
			LocalDateTime end = eventDTO.getEnd();

			if(start==null) throw new IllegalArgumentException("Start of event schedule cannot be blank");
			if(end==null) throw new IllegalArgumentException("End of event schedule cannot be blank");

			EventSchedule schedule = new EventSchedule();
			schedule.setStartDateTime(start);
			schedule.setEndDateTime(end);
			newEvent.setEventSchedule(schedule);
		} else {
			EventSchedule schedule = eventScheduleService.getEventSchedule(eventScheduleId);
			newEvent.setEventSchedule(schedule);
		}

        newEvent.setName(name);
        newEvent.setAddress(address);
        newEvent.setDescription(description);
        newEvent.setCapacity(capacity);
        newEvent.setCost(cost);
        newEvent.setEmail(email);
        newEvent.setPhoneNumber(phoneNumber);
        newEvent.setOrganizer(userService.getUser(organizerId));
        newEvent.setEventTypes(eventTypeService.getAllEventTypes(eventTypeIds));

		eventRepository.save(newEvent);

		return newEvent;
	}

	public Event updateEvent(EventDTO eventDTO) {
		UUID id = eventDTO.getId();
		String name = eventDTO.getName();
		String description = eventDTO.getDescription();
		Integer capacity = eventDTO.getCapacity();
		Double cost = eventDTO.getCost();
		String address = eventDTO.getAddress();
		String email = eventDTO.getEmail();
		String phoneNumber = eventDTO.getPhoneNumber();
		UUID organizerId = eventDTO.getOrganizerId();
		UUID eventScheduleId = eventDTO.getEventScheduleId();
		List<UUID> eventTypeIds = eventDTO.getEventTypeIds();

		Event event = getEvent(id);

		if (name != null && !name.equals("")){
			event.setName(name);
		}

		if(organizerId != null){
			event.setOrganizer(userService.getUser(organizerId));
		}

		if(eventTypeIds != null){
			List<EventType> eventTypes = eventTypeService.getAllEventTypes(eventTypeIds);
			event.setEventTypes(eventTypes);
		}

		if (eventScheduleId == null) {
			LocalDateTime start = eventDTO.getStart();
			LocalDateTime end = eventDTO.getEnd();

			if (start != null && end != null) {
				EventSchedule schedule = new EventSchedule();
				schedule.setStartDateTime(start);
				schedule.setEndDateTime(end);
				event.setEventSchedule(schedule);
			}
		} else {
			EventSchedule schedule = eventScheduleService.getEventSchedule(eventScheduleId);
			event.setEventSchedule(schedule);
		}
		
		if(description != null && !description.equals("")) {
			event.setDescription(description);
		}

		if(capacity != null && !capacity.equals(0)) {
			event.setCapacity(capacity);
		}

        if(cost != null && cost != event.getCost()) {
			event.setCost(cost);
		}

		if(address != null && !address.equals("")) {
			event.setAddress(address);
		}

        if(email != null && !email.equals("")) {
			event.setEmail(email);
		}

		if(phoneNumber != null && !phoneNumber.equals("")) {
			event.setPhoneNumber(phoneNumber);
		}

		eventRepository.save(event);

		return event;
	}

	public boolean deleteEvent(UUID id) {
		Event event = getEvent(id);
		eventRepository.delete(event);
		return true;
	}

	public Event getEvent(UUID id) {
		return eventRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Event " + id + " not found"));
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
		return eventRepository.findAll();
	}

}
