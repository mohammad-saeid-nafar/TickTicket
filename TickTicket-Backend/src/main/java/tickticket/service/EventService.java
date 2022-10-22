package tickticket.service;

import tickticket.dao.EventRepository;
import tickticket.dao.UserRepository;
import tickticket.dao.EventTypeRepository;
import tickticket.model.User;
import tickticket.model.Event;
import tickticket.model.EventType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class EventService {

    @Autowired
	private UserRepository userRepository;

    @Autowired
	private EventRepository eventRepository;

    @Autowired
	private EventTypeRepository eventTypeRepository;


    //Need to add, if the name is already taken 
    @Transactional
	public Event createEvent(String name, String description, Integer capacity, 
        Double cost, String address, String email, String phoneNumber, User organizer, List<EventType> eventTypes) {
        
        //If the name is already taken raise an exception
		Event event = getEventByName(name);

        if (event != null) throw new IllegalArgumentException("An event with this name already exists");

		if(name == null || name.equals("")) throw new IllegalArgumentException("Name cannot be blank");

        if(address == null || address.equals("")) throw new IllegalArgumentException("Address cannot be blank");

		if(email == null || email.equals("")) throw new IllegalArgumentException("Email cannot be blank");

		if(phoneNumber == null || phoneNumber.equals("")) throw new IllegalArgumentException("Phone number cannot be blank");

        if(capacity == null || capacity == 0) throw new IllegalArgumentException("Capacity cannot be blank or 0");

        if(cost == null ) throw new IllegalArgumentException("Cost cannot be blank");


        //define the services I need to create 

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
        //need to add everything for schedule 

		eventRepository.save(newEvent);

		return newEvent;
	}

    @Transactional
	public Event updateEvent(String name, String description, Integer capacity, 
    Double cost, String address, String email, String phoneNumber, User organizer, List<EventType> eventTypes) {

        if (name == null || name.equals("")){
            throw new IllegalArgumentException("Invalid event name");
        }

        //I can't update the name
		Event event = getEventByName(name);

        if (event == null){
            throw new IllegalArgumentException("Event not found");
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

	//Will need to delete the Event Schedule
	@Transactional
	public boolean deleteEvent(String name) {
		Event event = getEventByName(name);
		if(event==null) throw new IllegalArgumentException("Event not found");
		eventRepository.delete(event);
		return true;
	}

    @Transactional
    public Event getEventByName(String name){
        if(name == null) throw new IllegalArgumentException("Name cannot be blank");
        return eventRepository.findEventsByName(name);

    }

    @Transactional
	public List<Event> getAllEventsFromType(List<EventType> eventTypes) {
		return eventRepository.findEventsByEventTypesIn(eventTypes);
    }

    @Transactional
	public List<Event> getAllEventsFromOrganizer(User organizer) {
		return eventRepository.findEventsByOrganizer(organizer);

	}
	
	@Transactional
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
