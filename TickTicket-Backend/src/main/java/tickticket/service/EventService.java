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

        List<Event> allEvents = getAllEvents();
		Event event = getEventByName(name, allEvents);

        if (event != null) throw new IllegalArgumentException("The name is comimng");

		if(name ==null || name=="") throw new IllegalArgumentException("Name cannot be blank");

        if(address ==null || address=="") throw new IllegalArgumentException("Name cannot be blank");

		if(email ==null || email =="") throw new IllegalArgumentException("Email cannot be blank");

		if(phoneNumber ==null || phoneNumber =="") throw new IllegalArgumentException("phoneNumber cannot be blank");

        if(capacity ==null || capacity == 0) throw new IllegalArgumentException("Capacity cannot be blank or  0");

        if(cost ==null ) throw new IllegalArgumentException("Cost cannot be blank");


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
        List<Event> allEvents = getAllEvents();
		Event event = getEventByName(name, allEvents);

        if (event == null){
            throw new IllegalArgumentException("No excisting event match the name");
        }
		
		if(description != null && ! description.equals("")) {
			event.setDescription(description);
		} else {
			throw new IllegalArgumentException("Description cannot be blank.");
		}

		if(capacity != null && ! capacity.equals(0)) {
			event.setCapacity(capacity);
		} else {
			throw new IllegalArgumentException("Capacity cannot be null or of 0");
		}

        if(cost != null) {
			event.setCost(cost);
		} else {
			throw new IllegalArgumentException("Cost cannot be null");
		}

		if(address != null && ! address.equals("")) {
			event.setAddress(address);
		} else {
			throw new IllegalArgumentException("Address cannot be blank.");
		}

        if(email != null && ! email.equals("")) {
			event.setAddress(email);
		} else {
			throw new IllegalArgumentException("Email cannot be blank.");
		}

		if(phoneNumber != null && ! phoneNumber.equals("")) {
			event.setPhoneNumber(phoneNumber);
		} else {
			throw new IllegalArgumentException("Phone number cannot be blank.");
		}
		eventRepository.save(event);

		return event;
	}

//Will need to delete the Event Schedule
	@Transactional
	public boolean deleteEvent(String name) {
        List<Event> allEvents = getAllEvents();
		Event event = getEventByName(name, allEvents);
		if(event==null) throw new IllegalArgumentException("Event not found.");
		eventRepository.delete(event);
		return true;
	}

    @Transactional
    public Event  getEventByName(String name, List<Event> eventList){
        if(name == null) throw new IllegalArgumentException("Name can't be blank");
        else{
            for (Event element  : eventList){
                if (element.getName() == name){
                    return element;
                }
            }
            return null;
        }    
    }

    @Transactional
	public List<Event> getAllEventFromType(List<EventType> eventTypes) {
		return eventRepository.findEventsByEventTypesIn(eventTypes);
    }

    @Transactional
	public List<Event> getAllEventFromOrganizer(User username) {
		return eventRepository.findEventsByOrganizer(username);

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
